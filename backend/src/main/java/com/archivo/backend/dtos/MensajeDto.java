package com.archivo.backend.dtos;

public class MensajeDto {
    private String mensaje;

    public MensajeDto(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}