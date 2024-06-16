package com.example.appluissuscripciones.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.entidades.Usuario;
import com.example.appluissuscripciones.entidades.UsuarioLoginRequest;
import com.example.appluissuscripciones.entidades.UsuariosService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editMail, editPass;
    private Button btnLogin;
    private TextView registerLink;
    private UsuariosService usuariosService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editMail = findViewById(R.id.editMail);
        editPass = findViewById(R.id.editPass);
        btnLogin = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.registerLink);

        usuariosService = new UsuariosService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUsuario() {
        String correo = editMail.getText().toString().trim();
        String password = editPass.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Introduce correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioLoginRequest request = new UsuarioLoginRequest(correo, password);
        Call<Usuario> call = usuariosService.getUsuariosService().login(request);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuario = response.body();
                    if (usuario != null) {
                        // Login exitoso, redirige a la actividad SubsActivity
                        Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, SubsActivity.class);
                        intent.putExtra("id_usuario", usuario.getId_usuario());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al iniciar sesión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
