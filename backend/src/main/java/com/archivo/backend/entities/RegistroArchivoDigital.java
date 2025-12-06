package com.archivo.backend.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "registro_archivo_digital")
public class RegistroArchivoDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // --- RELACIONES E INGESTA ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caja_id")
    private Caja caja;

    @Column(name = "digitalizador_id")
    private Integer digitalizadorId;

    @Column(name = "digitalizador_raw", length = 100)
    private String digitalizadorRaw;

    // --- DATOS PRINCIPALES DEL DOCUMENTO ---
    // CORRECTO: Es String
    @Column(name = "numero_historia", nullable = false, length = 30)
    private String numeroHistoria;

    @Column(name = "nombre_paciente", nullable = false, length = 50)
    private String nombrePaciente;

    @Column(name = "formato", nullable = false, length = 50)
    private String formato;

    @Column(name = "ruta_archivo", length = 200)
    private String rutaArchivo;

    @Column(name = "fecha_carga", nullable = false)
    private LocalDateTime fechaCarga;

    // --- CLAVES FORÁNEAS NORMALIZADAS ---
    @Column(name = "sede_id")
    private Integer sedeId;

    @Column(name = "area_interna_id")
    private Integer areaInternaId;

    @Column(name = "serie_documental_id")
    private Integer serieDocumentalId;

    @Column(name = "tipo_archivo_id")
    private Integer tipoArchivoId;

    @Column(name = "nombre_documento_id")
    private Integer nombreDocumentoId;

    // --- CONTROL DE CALIDAD (QA) Y AUDITORÍA ---
    // CORRECTO: Es String
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "tipo_error", length = 50)
    private String tipoError;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "fecha_revision")
    private LocalDate fechaRevision;

    @Column(name = "revisado")
    private Boolean revisado = false;

    @Column(name = "corregido")
    private Boolean corregido = false;

    @Column(name = "total_produccion")
    private Integer totalProduccion;

    @Column(name = "muestra_3_porciento")
    private Integer muestra3Porciento;

    @Column(name = "porcentaje_error", precision = 5, scale = 2)
    private BigDecimal porcentajeError;

    // --- CAMPOS RAW (Ingesta Rápida desde Excel) ---

    // 🔑 CAMBIO CRÍTICO: Añadido para aceptar el valor de la columna NO obligatoria
    // del Excel
    @Column(name = "caja_raw", length = 50)
    private String cajaRaw;

    @Column(name = "sede_raw", nullable = false, length = 50)
    private String sedeRaw;

    @Column(name = "area_raw", nullable = false, length = 50)
    private String areaRaw;

    @Column(name = "serie_doc_raw", nullable = false, length = 50)
    private String serieDocRaw;

    @Column(name = "tipo_archivo_raw", nullable = false, length = 50)
    private String tipoArchivoRaw;

    @Column(name = "nombre_documento_raw", nullable = false, length = 100)
    private String nombreDocumentoRaw;

    @Column(name = "responsable_revision_raw", length = 100)
    private String responsableRevisionRaw;

    // **********************************************
    // Getters y Setters
    // **********************************************

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    // 🔑 GETTER/SETTER de cajaRaw añadido
    public String getCajaRaw() {
        return cajaRaw;
    }

    public void setCajaRaw(String cajaRaw) {
        this.cajaRaw = cajaRaw;
    }
    // Fin GETTER/SETTER cajaRaw

    public Integer getDigitalizadorId() {
        return digitalizadorId;
    }

    public void setDigitalizadorId(Integer digitalizadorId) {
        this.digitalizadorId = digitalizadorId;
    }

    public String getDigitalizadorRaw() {
        return digitalizadorRaw;
    }

    public void setDigitalizadorRaw(String digitalizadorRaw) {
        this.digitalizadorRaw = digitalizadorRaw;
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

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
    }

    public Integer getAreaInternaId() {
        return areaInternaId;
    }

    public void setAreaInternaId(Integer areaInternaId) {
        this.areaInternaId = areaInternaId;
    }

    public Integer getSerieDocumentalId() {
        return serieDocumentalId;
    }

    public void setSerieDocumentalId(Integer serieDocumentalId) {
        this.serieDocumentalId = serieDocumentalId;
    }

    public Integer getTipoArchivoId() {
        return tipoArchivoId;
    }

    public void setTipoArchivoId(Integer tipoArchivoId) {
        this.tipoArchivoId = tipoArchivoId;
    }

    public Integer getNombreDocumentoId() {
        return nombreDocumentoId;
    }

    public void setNombreDocumentoId(Integer nombreDocumentoId) {
        this.nombreDocumentoId = nombreDocumentoId;
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

    public String getResponsableRevisionRaw() {
        return responsableRevisionRaw;
    }

    public void setResponsableRevisionRaw(String responsableRevisionRaw) {
        this.responsableRevisionRaw = responsableRevisionRaw;
    }
}