package com.example.appluissuscripciones.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionResponse;
import com.example.appluissuscripciones.entidades.SuscripcionesService;
import com.example.appluissuscripciones.interfaces.Suscripciones;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSubActivity extends AppCompatActivity {

    private EditText editNombre, editFechaInicio, editFechaFin, editImporte, editNotas, editPeriodicidad;
    private ImageView logoEscogido;
    private Calendar calendar;

    private SimpleDateFormat dateFormat;

    private Button buttonActualizar, buttonEliminar;
    private int idUsuario, idSuscripcion;
    private Suscripciones suscripcionesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);

        SuscripcionesService suscripcionesServiceInstance = new SuscripcionesService();
        suscripcionesService = suscripcionesServiceInstance.getSuscripcionesService();

        editNombre = findViewById(R.id.editNombre);
        editFechaInicio = findViewById(R.id.editFechaInicio);
        editFechaFin = findViewById(R.id.editFechaFin);
        editImporte = findViewById(R.id.editImporte);
        editNotas = findViewById(R.id.editNotas);
        editPeriodicidad = findViewById(R.id.editPeriodicidad);
        logoEscogido = findViewById(R.id.logoescogido);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        // Inicializar calendario y formato de fecha
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Obtener el id de usuario y de suscripción
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("id_usuario", -1);
        idSuscripcion = getIntent().getIntExtra("id_suscripcion", -1);

        // Cargar los detalles de la suscripción existente
        if (idUsuario != -1 && idSuscripcion != -1) {
            obtenerDetallesSuscripcion(idUsuario, idSuscripcion);
        } else {
            // Manejar el caso donde los ids no son válidos
            Toast.makeText(this, "No se pudo obtener la suscripción", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Configurar botón de Eliminar
        buttonEliminar.setOnClickListener(v -> mostrarDialogoConfirmacion());

    }

    // Método para obtener los detalles de la suscripción desde la API
    private void obtenerDetallesSuscripcion(int idUsuario, int idSuscripcion) {
        Call<Suscripcion> call = suscripcionesService.obtenerDetalleSuscripcion(idUsuario, idSuscripcion);
        call.enqueue(new Callback<Suscripcion>() {
            @Override
            public void onResponse(Call<Suscripcion> call, Response<Suscripcion> response) {
                if (response.isSuccessful()) {
                    Suscripcion suscripcion = response.body();
                    if (suscripcion != null) {
                        mostrarDetallesSuscripcion(suscripcion);
                    } else {
                        Toast.makeText(EditSubActivity.this, "No se encontraron detalles de la suscripción", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(EditSubActivity.this, "Error al obtener detalles de la suscripción", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Suscripcion> call, Throwable t) {
                Toast.makeText(EditSubActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // Método para mostrar los detalles de la suscripción en las vistas
    private void mostrarDetallesSuscripcion(Suscripcion suscripcion) {
        editNombre.setText(suscripcion.getNombreSuscripcion());
        editFechaInicio.setText(suscripcion.getFechaInicio());
        editFechaFin.setText(suscripcion.getFechaFin());
        editImporte.setText(String.valueOf(suscripcion.getImporte()) + "€");
        editNotas.setText(suscripcion.getNotas());
        editPeriodicidad.setText(suscripcion.getPeriodicidad());

        if (suscripcion.getLogo() != null && !suscripcion.getLogo().isEmpty()) {
            byte[] decodedString = Base64.decode(suscripcion.getLogo(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            logoEscogido.setImageBitmap(decodedByte);
        } else {
            logoEscogido.setImageResource(R.drawable.ic_default_image); // Imagen por defecto
        }

        String[] periodicidades = getResources().getStringArray(R.array.periodicidad_array);
        int index = -1;
        for (int i = 0; i < periodicidades.length; i++) {
            if (periodicidades[i].equals(suscripcion.getPeriodicidad())) {
                index = i;
                break;
            }
        }

    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar suscripción");
        builder.setMessage("¿Estás seguro de que quieres eliminar esta suscripción?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarSuscripcion();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario canceló, no hacer nada
            }
        });
        builder.show();
    }

    // Método para eliminar la suscripción
    private void eliminarSuscripcion() {
        Call<Void> call = suscripcionesService.eliminarSuscripcion(idUsuario, idSuscripcion);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditSubActivity.this, "Suscripción eliminada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditSubActivity.this, SubsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditSubActivity.this, "Error al eliminar la suscripción", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditSubActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
