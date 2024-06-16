package com.example.appluissuscripciones.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.entidades.Usuario;
import com.example.appluissuscripciones.interfaces.Usuarios;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmailLogin, editPasswordLogin;
    private Button btnLogin;

    private Usuarios usuariosService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmailLogin = findViewById(R.id.editEmailLogin);
        editPasswordLogin = findViewById(R.id.editPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tu-direccion-api/")  // Reemplaza con tu dirección base de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuariosService = retrofit.create(Usuarios.class);

        // Listener para el botón de Iniciar Sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }

    // Método para iniciar sesión
    private void iniciarSesion() {
        String correo = editEmailLogin.getText().toString().trim();
        String password = editPasswordLogin.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Introduce correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto JSON para enviar al servidor
        JsonObject json = new JsonObject();
        json.addProperty("correo", correo);
        json.addProperty("password", password);

        // Llamada a la API para verificar credenciales
        Call<ResponseBody> call = usuariosService.login(json);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, el inicio de sesión fue correcto
                    Toast.makeText(getApplicationContext(), "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show();

                    // Redirigir a la siguiente actividad (SubsActivity)
                    Intent intent = new Intent(LoginActivity.this, SubsActivity.class);
                    startActivity(intent);
                    finish(); // Para evitar volver a esta actividad desde el botón de retroceso
                } else {
                    // Si no es exitosa, mostrar un mensaje de error
                    Toast.makeText(getApplicationContext(), "Credenciales incorrectas. Vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Error en la llamada a la API (p. ej., conexión fallida)
                Toast.makeText(getApplicationContext(), "Error de conexión. Vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
