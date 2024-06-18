package com.example.appluissuscripciones.interfaces;

import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Suscripciones {
    @POST("suscripciones")
    Call<SuscripcionResponse> guardarSuscripcion(@Body Suscripcion suscripcion);

    @GET("/usuarios/{id_usuario}/suscripciones")
    Call<SuscripcionResponse> obtenerSuscripciones(@Path("id_usuario") int idUsuario);

    @GET("usuarios/{id_usuario}/suscripciones/{id_suscripcion}")
    Call<Suscripcion> obtenerDetalleSuscripcion(
            @Path("id_usuario") int idUsuario,
            @Path("id_suscripcion") int idSuscripcion
    );

    @DELETE("usuarios/{id_usuario}/suscripciones/{id_suscripcion}")
    Call<Void> eliminarSuscripcion(
            @Path("id_usuario") int idUsuario,
            @Path("id_suscripcion") int idSuscripcion
    );

}
