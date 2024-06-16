package com.example.appluissuscripciones.interfaces;

import com.example.appluissuscripciones.entidades.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Usuarios {

    @POST("usuarios")
    Call<Usuario> crearUsuario(@Body Usuario usuario);
    @POST("usuarios")
    Call<ResponseBody> comprobarUsuario(@Body Usuario usuario);

}
