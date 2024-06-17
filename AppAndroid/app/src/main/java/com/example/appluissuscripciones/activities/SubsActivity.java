package com.example.appluissuscripciones.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appluissuscripciones.R;

public class SubsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subs);
        int idUsuario = 0;

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("id_usuario")) {
            idUsuario = sharedPreferences.getInt("id_usuario", -1);
        }

        Button btn = findViewById(R.id.btn);
        int finalIdUsuario = idUsuario;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubsActivity.this, AddSubsActivity.class);
                intent.putExtra("id_usuario", finalIdUsuario);
                startActivity(intent);
            }
        });

        TextView t1 = findViewById(R.id.nombreUsuario);
        t1.setText(String.valueOf(idUsuario));


    }
}