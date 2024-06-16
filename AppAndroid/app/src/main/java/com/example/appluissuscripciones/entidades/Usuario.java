package com.example.appluissuscripciones.entidades;

public class Usuario {
    private int id_usuario;
    private String correo;
    private String password;

    public Usuario(int id_usuario, String correo, String password) {
        this.id_usuario = id_usuario;
        this.correo = correo;
        this.password = password;
    }

    public Usuario(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public Usuario() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}