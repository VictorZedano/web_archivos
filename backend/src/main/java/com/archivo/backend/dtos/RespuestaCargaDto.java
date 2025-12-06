package com.archivo.backend.dtos;

import java.util.ArrayList;
import java.util.List;

public class RespuestaCargaDto {

    private String mensaje = "Proceso de carga masiva finalizado.";
    
    // Contadores de éxito y error
    private Integer registrosNormalizadosConExito = 0;
    private Integer registrosConErrores = 0;
    
    // Lista de errores específicos por fila
    private List<String> erroresDeNormalizacion = new ArrayList<>();
    
    // **********************************************
    // Constructor
    // **********************************************

    public RespuestaCargaDto() {
        // Inicializa contadores
    }

    // **********************************************
    // Getters y Setters
    // **********************************************

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getRegistrosNormalizadosConExito() {
        return registrosNormalizadosConExito;
    }

    public void setRegistrosNormalizadosConExito(Integer registrosNormalizadosConExito) {
        this.registrosNormalizadosConExito = registrosNormalizadosConExito;
    }

    public Integer getRegistrosConErrores() {
        return registrosConErrores;
    }

    public void setRegistrosConErrores(Integer registrosConErrores) {
        this.registrosConErrores = registrosConErrores;
    }

    public List<String> getErroresDeNormalizacion() {
        return erroresDeNormalizacion;
    }

    public void setErroresDeNormalizacion(List<String> erroresDeNormalizacion) {
        this.erroresDeNormalizacion = erroresDeNormalizacion;
    }
}