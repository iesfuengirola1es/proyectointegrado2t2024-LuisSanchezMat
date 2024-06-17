package com.example.appluissuscripciones.interfaces;

import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Suscripciones {
    @POST("suscripciones")
    Call<SuscripcionResponse> guardarSuscripcion(@Body Suscripcion suscripcion);

    @GET("/usuarios/{id_usuario}/suscripciones")
    Call<SuscripcionResponse> obtenerSuscripciones(@Path("id_usuario") int idUsuario);
}
