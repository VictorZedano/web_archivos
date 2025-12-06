package com.archivo.backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistroExcelDTO {

    private String codigoCaja; // Columna 1
    private String numeroHistoria; // Columna 9
    private String nombrePaciente; // Columna 10
    private String formato; // Columna 13
    private String rutaArchivo; // Columna 14

    // --- CAMPOS RAW ---
    private String sedeRaw; // Columna 6
    private String areaRaw; // Columna 7
    private String serieDocRaw; // Columna 8
    private String tipoArchivoRaw; // Columna 11
    private String nombreDocumentoRaw; // Columna 12

    // --- DATOS INYECTADOS/SOBRESCRITOS DESDE EXCEL ---
    private String digitalizadorRaw; // Columna 15 (Nombre del Digitalizador en texto)
    private LocalDateTime fechaCarga; // Columna 16
    private String status; // Columna 17

    // --- QA ---
    private String tipoError; // Columna 18
    private String observaciones; // Columna 19
    private String responsableRevisionRaw; // Columna 20
    private LocalDate fechaRevision; // Columna 21
    private Boolean revisado; // Columna 22
    private Boolean corregido; // Columna 23
    private Integer totalProduccion; // Columna 24
    private Integer muestra3Porciento; // Columna 25
    private BigDecimal porcentajeError; // Columna 26

    // **********************************************
    // Getters y Setters
    // **********************************************

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public String getNumeroHistoria() {
        return numeroHistoria;
    }

    public void setNumeroHistoria(String numeroHistoria) {
        this.numeroHistoria = numeroHistoria;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getSedeRaw() {
        return sedeRaw;
    }

    public void setSedeRaw(String sedeRaw) {
        this.sedeRaw = sedeRaw;
    }

    public String getAreaRaw() {
        return areaRaw;
    }

    public void setAreaRaw(String areaRaw) {
        this.areaRaw = areaRaw;
    }

    public String getSerieDocRaw() {
        return serieDocRaw;
    }

    public void setSerieDocRaw(String serieDocRaw) {
        this.serieDocRaw = serieDocRaw;
    }

    public String getTipoArchivoRaw() {
        return tipoArchivoRaw;
    }

    public void setTipoArchivoRaw(String tipoArchivoRaw) {
        this.tipoArchivoRaw = tipoArchivoRaw;
    }

    public String getNombreDocumentoRaw() {
        return nombreDocumentoRaw;
    }

    public void setNombreDocumentoRaw(String nombreDocumentoRaw) {
        this.nombreDocumentoRaw = nombreDocumentoRaw;
    }

    public String getDigitalizadorRaw() {
        return digitalizadorRaw;
    }

    public void setDigitalizadorRaw(String digitalizadorRaw) {
        this.digitalizadorRaw = digitalizadorRaw;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoError() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResponsableRevisionRaw() {
        return responsableRevisionRaw;
    }

    public void setResponsableRevisionRaw(String responsableRevisionRaw) {
        this.responsableRevisionRaw = responsableRevisionRaw;
    }

    public LocalDate getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(LocalDate fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(Boolean revisado) {
        this.revisado = revisado;
    }

    public Boolean getCorregido() {
        return corregido;
    }

    public void setCorregido(Boolean corregido) {
        this.corregido = corregido;
    }

    public Integer getTotalProduccion() {
        return totalProduccion;
    }

    public void setTotalProduccion(Integer totalProduccion) {
        this.totalProduccion = totalProduccion;
    }

    public Integer getMuestra3Porciento() {
        return muestra3Porciento;
    }

    public void setMuestra3Porciento(Integer muestra3Porciento) {
        this.muestra3Porciento = muestra3Porciento;
    }

    public BigDecimal getPorcentajeError() {
        return porcentajeError;
    }

    public void setPorcentajeError(BigDecimal porcentajeError) {
        this.porcentajeError = porcentajeError;
    }
}