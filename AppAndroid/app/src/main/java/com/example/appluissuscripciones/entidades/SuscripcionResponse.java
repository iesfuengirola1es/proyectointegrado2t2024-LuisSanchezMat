package com.example.appluissuscripciones.entidades;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuscripcionResponse {
    @SerializedName("id_suscripcion")
    private int idSuscripcion;

    @SerializedName("nombre_suscripcion")
    private String nombreSuscripcion;

    @SerializedName("suscripciones")
    private List<Suscripcion> suscripciones;

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(List<Suscripcion> suscripciones) {
        this.suscripciones = suscripciones;
    }

    @SerializedName("logo")
    private String logo;

    @SerializedName("fecha_inicio")
    private String fechaInicio;

    @SerializedName("fecha_fin")
    private String fechaFin;

    @SerializedName("periodicidad")
    private String periodicidad;

    @SerializedName("importe")
    private double importe;

    @SerializedName("notas")
    private String notas;

    @SerializedName("id_usuario")
    private int idUsuario;

    @SerializedName("id_servicio")
    private Integer idServicio; // Usamos Integer para permitir que sea nulo

    // Constructor vacío necesario para deserialización con Gson
    public SuscripcionResponse() {
    }

    // Getters y Setters
    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getNombreSuscripcion() {
        return nombreSuscripcion;
    }

    public void setNombreSuscripcion(String nombreSuscripcion) {
        this.nombreSuscripcion = nombreSuscripcion;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }
}

