package com.example.appluissuscripciones.interfaces;

import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Suscripciones {
    @POST("suscripciones")
    Call<SuscripcionResponse> guardarSuscripcion(@Body Suscripcion suscripcion);
}
