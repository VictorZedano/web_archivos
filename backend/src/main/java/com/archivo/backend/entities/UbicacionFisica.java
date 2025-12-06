package com.archivo.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ubicacion_fisica")
public class UbicacionFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // La sede a la que pertenece esta ubicación física (relación ManyToOne si
    // mapeas Sede)
    @Column(name = "sede_id", nullable = false)
    private Integer sedeId;

    @Column(name = "estante", nullable = false, length = 20)
    private String estante;

    @Column(name = "columna", nullable = false)
    private Integer columna;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    // El código único que identifica la coordenada (ej: S-NORTE-E01-C05-N03)
    @Column(name = "codigo_ubicacion", unique = true, nullable = false, length = 50)
    private String codigoUbicacion;

    // **********************************************
    // Constructores (Opcional, pero recomendado)
    // **********************************************

    public UbicacionFisica() {
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

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getCodigoUbicacion() {
        return codigoUbicacion;
    }

    public void setCodigoUbicacion(String codigoUbicacion) {
        this.codigoUbicacion = codigoUbicacion;
    }
}