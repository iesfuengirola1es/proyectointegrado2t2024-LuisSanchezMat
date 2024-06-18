package com.example.appluissuscripciones.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.adapters.SubsAdapter;
import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionResponse;
import com.example.appluissuscripciones.entidades.SuscripcionesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubsActivity extends AppCompatActivity {

    private ListView listViewSubs;
    private SubsAdapter subsAdapter;
    private Button buttonCerrarSesion, buttonAgregar;
    private SuscripcionesService suscripcionesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subs);

        listViewSubs = findViewById(R.id.listview);

        suscripcionesService = new SuscripcionesService();

        // Obtener el ID del usuario de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("id_usuario", -1);

        if (idUsuario != -1) {
            obtenerSuscripciones(idUsuario);
        } else {
            Toast.makeText(this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
        }

        buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubsActivity.this, AddSubsActivity.class);
                startActivity(intent);
            }
        });


        buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("id_usuario");
                editor.apply();
                Intent intent = new Intent(SubsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void obtenerSuscripciones(int idUsuario) {
        Call<SuscripcionResponse> call = suscripcionesService.getSuscripcionesService().obtenerSuscripciones(idUsuario);
        call.enqueue(new Callback<SuscripcionResponse>() {
            @Override
            public void onResponse(Call<SuscripcionResponse> call, Response<SuscripcionResponse> response) {
                if (response.isSuccessful()) {
                    List<Suscripcion> suscripciones = response.body().getSuscripciones();
                    subsAdapter = new SubsAdapter(SubsActivity.this, suscripciones);
                    listViewSubs.setAdapter(subsAdapter);
                }
            }

            @Override
            public void onFailure(Call<SuscripcionResponse> call, Throwable t) {
                Toast.makeText(SubsActivity.this, "Error al conectar con la API", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }


        });
    }
}
