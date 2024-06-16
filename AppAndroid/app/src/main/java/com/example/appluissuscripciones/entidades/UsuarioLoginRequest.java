package com.example.appluissuscripciones.entidades;

public class UsuarioLoginRequest {
    private String correo;
    private String password;

    public UsuarioLoginRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }
}

