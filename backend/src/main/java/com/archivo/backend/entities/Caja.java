package com.archivo.backend.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "caja")
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", unique = true, length = 30)
    private String codigo;

    @Column(name = "codigo_qr", unique = true, length = 50)
    private String codigoQr;

    @Column(name = "contenido", length = 100)
    private String contenido;

    @Column(name = "estado", length = 20)
    private String estado = "ALMACENADA"; // Valor por defecto en Java

    // Relación Many-to-One: Muchas cajas tienen una UbicacionFisica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private UbicacionFisica ubicacion;

    @Column(name = "sede_id")
    private Integer sedeId;

    @Column(name = "usuario_creador_id")
    private Integer usuarioCreadorId;

    @Column(name = "fecha_inicio_contenido")
    private LocalDate fechaInicioContenido;

    @Column(name = "fecha_creado")
    private LocalDateTime fechaCreado;

    /**
     * Método de ciclo de vida de JPA. Se ejecuta automáticamente justo antes de que
     * la entidad
     * sea persistida (guardada por primera vez) en la base de datos.
     * Esto garantiza que 'fechaCreado' siempre tenga un valor actual.
     */
    @PrePersist
    protected void onCreate() {
        if (fechaCreado == null) {
            fechaCreado = LocalDateTime.now();
        }
        // El campo 'estado' ya tiene "ALMACENADA" por la inicialización de Java,
        // por lo que no necesita chequeo adicional aquí, a menos que se quiera forzar
        // en caso de que alguien lo haya seteado a null.
    }

    // **********************************************
    // Getters y Setters
    // **********************************************

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UbicacionFisica getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionFisica ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
    }

    public Integer getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(Integer usuarioCreadorId) {
        this.usuarioCreadorId = usuarioCreadorId;
    }

    public LocalDate getFechaInicioContenido() {
        return fechaInicioContenido;
    }

    public void setFechaInicioContenido(LocalDate fechaInicioContenido) {
        this.fechaInicioContenido = fechaInicioContenido;
    }

    public LocalDateTime getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(LocalDateTime fechaCreado) {
        this.fechaCreado = fechaCreado;
    }
}