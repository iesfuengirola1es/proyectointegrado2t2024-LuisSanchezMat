package com.example.appluissuscripciones.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.entidades.Usuario;
import com.example.appluissuscripciones.interfaces.Usuarios;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText editMail, editPass;
    private Button btnRegister;
    private TextView loginLink;

    private Usuarios usuariosService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editMail = findViewById(R.id.editMail);
        editPass = findViewById(R.id.editPass);
        btnRegister = findViewById(R.id.btnRegister);
        loginLink = findViewById(R.id.loginLink);

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://luissanchezmat.eu.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuariosService = retrofit.create(Usuarios.class);

        // Listener para el botón de Registrarse
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();

            }
        });

        // Listener para el texto de loginLink
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    // Listener para el texto de loginLink

    // Método para registrar usuario
    private void registrarUsuario() {
        String correo = editMail.getText().toString().trim();
        String password = editPass.getText().toString().trim();

        // Validación de los campos
        if (correo.isEmpty() || password.isEmpty() || !correo.contains("@") || !correo.contains(".") || password.length() < 8) {
            // Manejo de validación si los campos están vacíos o no válidos
            Toast.makeText(getApplicationContext(), "Introduzca datos válidos", Toast.LENGTH_SHORT).show();
            return; // Detener el proceso de registro si los datos no son válidos
        }

        // Crear objeto Usuario solo si los datos son válidos
        Usuario usuario = new Usuario(correo, password);

        // Llamada a la API para registrar usuario
        Call<Usuario> call = usuariosService.crearUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    // Registro exitoso
                    Usuario nuevoUsuario = response.body();
                    Toast.makeText(getApplicationContext(), "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
                    // Aquí puedes redirigir a la siguiente pantalla o hacer algo adicional
                } else {
                    // Error en la respuesta de la API
                    Toast.makeText(getApplicationContext(), "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Error en la llamada a la API
                Toast.makeText(getApplicationContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}