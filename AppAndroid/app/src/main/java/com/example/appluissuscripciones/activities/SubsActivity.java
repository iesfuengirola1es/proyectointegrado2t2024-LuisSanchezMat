package com.example.appluissuscripciones.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appluissuscripciones.R;

public class SubsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subs);


        int id = getIntent().getIntExtra("id_usuario", 0); // 0 es el valor por defecto si no se encuentra el extra
        TextView t1 = findViewById(R.id.nombreUsuario);
        t1.setText(String.valueOf(id));


    }
}