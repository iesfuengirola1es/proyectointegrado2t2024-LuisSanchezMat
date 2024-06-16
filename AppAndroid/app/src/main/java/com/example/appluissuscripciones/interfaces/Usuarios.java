package com.example.appluissuscripciones.interfaces;

import com.example.appluissuscripciones.entidades.Usuario;
import com.example.appluissuscripciones.entidades.UsuarioLoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Usuarios {

    @POST("usuarios")
    Call<Usuario> crearUsuario(@Body Usuario usuario);
    @POST("login")
    Call<Usuario> login(@Body UsuarioLoginRequest request);

}
