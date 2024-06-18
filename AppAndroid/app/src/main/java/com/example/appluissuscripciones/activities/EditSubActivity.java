package com.example.appluissuscripciones.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        // Inicializar Retrofit correctamente usando SuscripcionesService
        SuscripcionesService suscripcionesServiceInstance = new SuscripcionesService();
        suscripcionesService = suscripcionesServiceInstance.getSuscripcionesService();

        // Obtener referencias a vistas del layout
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
        buttonEliminar.setOnClickListener(v -> eliminarSuscripcion());
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
                        // Mostrar los datos de la suscripción en las vistas correspondientes
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
        String[] periodicidades = getResources().getStringArray(R.array.periodicidad_array);


        int index = -1;
        for (int i = 0; i < periodicidades.length; i++) {
            if (periodicidades[i].equals(suscripcion.getPeriodicidad())) {
                index = i;
                break;
            }
        }

        // Cargar el logo, si está disponible
        // Aquí debes implementar la lógica para mostrar el logo en la ImageView logoEscogido
    }

    // Método para actualizar la suscripción
    private void actualizarSuscripcion() {
        // Obtener los datos actualizados de las vistas
        String nombre = editNombre.getText().toString().trim();
        String fechaInicio = editFechaInicio.getText().toString().trim();
        String fechaFin = editFechaFin.getText().toString().trim();
        double importe = Double.parseDouble(editImporte.getText().toString().trim());
        String notas = editNotas.getText().toString().trim();



        // Crear objeto Suscripcion con los datos actualizados
        Suscripcion suscripcionActualizada = new Suscripcion();
        suscripcionActualizada.setIdSuscripcion(idSuscripcion);
        suscripcionActualizada.setNombreSuscripcion(nombre);
        suscripcionActualizada.setFechaInicio(fechaInicio);
        suscripcionActualizada.setFechaFin(fechaFin);
        suscripcionActualizada.setImporte(importe);
        suscripcionActualizada.setNotas(notas);


        // Llamar al servicio para actualizar la suscripción
        Call<SuscripcionResponse> call = suscripcionesService.actualizarSuscripcion(idUsuario, idSuscripcion, suscripcionActualizada);
        call.enqueue(new Callback<SuscripcionResponse>() {
            @Override
            public void onResponse(Call<SuscripcionResponse> call, Response<SuscripcionResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditSubActivity.this, "Suscripción actualizada correctamente", Toast.LENGTH_SHORT).show();
                    // Puedes agregar más acciones después de actualizar, como cerrar la actividad o actualizar la UI
                } else {
                    Toast.makeText(EditSubActivity.this, "Error al actualizar la suscripción", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuscripcionResponse> call, Throwable t) {
                Toast.makeText(EditSubActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para eliminar la suscripción
    private void eliminarSuscripcion() {
        // Confirmar la eliminación
        // Aquí podrías mostrar un diálogo de confirmación antes de proceder con la eliminación

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
