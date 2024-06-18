package com.example.appluissuscripciones.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionResponse;
import com.example.appluissuscripciones.entidades.SuscripcionesService;
import com.example.appluissuscripciones.interfaces.Suscripciones;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubsActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 2;

    private EditText editNombre, editFechaInicio, editFechaFin, editImporte, editNotas;
    private Spinner spinnerPeriodicidad;
    private ImageView logoEscogido;
    private Button buttonChooseLogo, buttonGuardar;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private Bitmap selectedImageBitmap;

    private Suscripciones suscripcionesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);

        SuscripcionesService suscripcionesServiceInstance = new SuscripcionesService();
        suscripcionesService = suscripcionesServiceInstance.getSuscripcionesService();

        editNombre = findViewById(R.id.editNombre);
        editFechaInicio = findViewById(R.id.editFechaInicio);
        editFechaFin = findViewById(R.id.editFechaFin);
        editImporte = findViewById(R.id.editImporte);
        editNotas = findViewById(R.id.editNotas);
        spinnerPeriodicidad = findViewById(R.id.spinnerPeriodicidad);
        logoEscogido = findViewById(R.id.logoescogido);
        buttonChooseLogo = findViewById(R.id.buttonChooseLogo);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        // Inicializar calendario y formato de fecha
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Configurar el spinner de periodicidad
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.periodicidad_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodicidad.setAdapter(adapter);
        spinnerPeriodicidad.setSelection(0); // Establecer la primera opción como seleccionada por defecto

        // Configurar selección de fechas
        editFechaInicio.setOnClickListener(v -> showDatePickerDialog(editFechaInicio));
        editFechaFin.setOnClickListener(v -> showDatePickerDialog(editFechaFin));

        // Botón para elegir un logo
        buttonChooseLogo.setOnClickListener(v -> chooseLogo());

        // Botón para guardar la suscripción
        buttonGuardar.setOnClickListener(v -> {
            guardarSuscripcion();
        });
    }

    // Mostrar el selector de fechas
    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editText.setText(dateFormat.format(calendar.getTime()));
        };

        new DatePickerDialog(
                this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    // Abrir galería para seleccionar un logo
    private void chooseLogo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), REQUEST_IMAGE_PICK);
    }

    // Manejar el resultado de la selección de la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_PICK && data != null && data.getData() != null) {
            try {
                // Obtener el bitmap de la imagen seleccionada
                Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                // Redimensionar el bitmap
                selectedImageBitmap = resizeBitmap(originalBitmap, 128, 128);

                // Mostrar el bitmap redimensionado en ImageView
                logoEscogido.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Reducir el tamaño del bitmap antes de la conversión a Base64
    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (ratioMax > ratioBitmap) {
            finalWidth = (int) ((float) maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) ((float) maxWidth / ratioBitmap);
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
        return resizedBitmap;
    }


    // Validar y guardar la suscripción
    private void guardarSuscripcion() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE);
        int idUsuario = 0;
        if (sharedPreferences.contains("id_usuario")) {
            idUsuario = sharedPreferences.getInt("id_usuario", -1);
        }
        // Obtener los datos de los EditText y Spinner
        String nombre = editNombre.getText().toString().trim();
        String fechaInicioStr = editFechaInicio.getText().toString().trim();
        String fechaFinStr = editFechaFin.getText().toString().trim();
        String importeStr = editImporte.getText().toString().trim();
        String notas = editNotas.getText().toString().trim();
        String periodicidad = spinnerPeriodicidad.getSelectedItem().toString();

        // Validar campos vacíos
        if (nombre.isEmpty() || fechaInicioStr.isEmpty() || fechaFinStr.isEmpty() || importeStr.isEmpty() || notas.isEmpty() || periodicidad.equals("Elegir Periodicidad")) {
            Toast.makeText(this, "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
            return;
        }

        double importe = Double.parseDouble(importeStr);

        // Convertir el logo seleccionado a Base64
        String logoBase64 = "";
        if (selectedImageBitmap != null) {
            logoBase64 = bitmapToBase64(selectedImageBitmap);
        }

        // Crear objeto Suscripcion
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setNombreSuscripcion(nombre);

        // Convertir fechas de String a Date usando el formato adecuado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date fechaInicioDate = dateFormat.parse(fechaInicioStr);
            Date fechaFinDate = dateFormat.parse(fechaFinStr);

            // Configurar fechas en el objeto Suscripcion
            suscripcion.setFechaInicio(dateFormat.format(fechaInicioDate));
            suscripcion.setFechaFin(dateFormat.format(fechaFinDate));

            // Configurar las fechas en el calendario (opcional, si necesitas manipularlas)
            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.setTime(fechaInicioDate);
            Calendar fechaFin = Calendar.getInstance();
            fechaFin.setTime(fechaFinDate);

            // Validar que fechaFin sea posterior a fechaInicio
            if (fechaFinDate.before(fechaInicioDate)) {
                Toast.makeText(this, "La fecha de fin debe ser posterior a la fecha de inicio", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al parsear las fechas", Toast.LENGTH_SHORT).show();
            return;
        }

        suscripcion.setImporte(importe);
        suscripcion.setNotas(notas);
        suscripcion.setPeriodicidad(periodicidad);
        suscripcion.setIdUsuario(idUsuario);
        suscripcion.setLogo(logoBase64);

        // Llamar al servicio para guardar la suscripción
        Call<SuscripcionResponse> call = suscripcionesService.guardarSuscripcion(suscripcion);
        call.enqueue(new Callback<SuscripcionResponse>() {
            @Override
            public void onResponse(Call<SuscripcionResponse> call, Response<SuscripcionResponse> response) {
                if (response.isSuccessful()) {
                    SuscripcionResponse suscripcionResponse = response.body();
                    if (suscripcionResponse != null) {
                        Toast.makeText(AddSubsActivity.this, "Suscripción guardada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddSubsActivity.this, SubsActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(AddSubsActivity.this, "Error: Ya tienes una suscripción a ese nombre", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuscripcionResponse> call, Throwable t) {
                Toast.makeText(AddSubsActivity.this, "Error al conectar con la API", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    // Método para convertir Bitmap a Base64
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
}
