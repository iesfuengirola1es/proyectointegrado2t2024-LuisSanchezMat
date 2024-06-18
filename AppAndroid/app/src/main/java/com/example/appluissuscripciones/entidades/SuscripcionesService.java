package com.example.appluissuscripciones.entidades;

import com.example.appluissuscripciones.interfaces.Suscripciones;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuscripcionesService {

    private static final String BASE_URL = "https://luissanchezmat.eu.pythonanywhere.com/";

    private Suscripciones suscripcionesService;

    public SuscripcionesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        suscripcionesService = retrofit.create(Suscripciones.class);
    }

    public Suscripciones getSuscripcionesService() {
        return suscripcionesService;
    }

    public void setSuscripcionesService(Suscripciones suscripcionesService) {
        this.suscripcionesService = suscripcionesService;
    }
}
