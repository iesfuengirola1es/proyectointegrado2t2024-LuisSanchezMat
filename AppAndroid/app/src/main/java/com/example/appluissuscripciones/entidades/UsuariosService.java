package com.example.appluissuscripciones.entidades;
import com.example.appluissuscripciones.interfaces.Usuarios;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuariosService {

    private static final String BASE_URL = "https://luissanchezmat.eu.pythonanywhere.com/";

    private Usuarios usuariosService;

    public UsuariosService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuariosService = retrofit.create(Usuarios.class);
    }

    public Usuarios getUsuariosService() {
        return usuariosService;
    }
}

