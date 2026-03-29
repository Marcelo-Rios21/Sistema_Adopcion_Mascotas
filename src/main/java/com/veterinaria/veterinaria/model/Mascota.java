package com.veterinaria.veterinaria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria.")
    @Column(nullable = false, length = 50)
    private String especie;

    @NotBlank(message = "La raza es obligatoria.")
    @Column(nullable = false, length = 50)
    private String raza;

    @NotNull(message = "La edad es obligatoria.")
    @Min(value = 0, message = "La edad no puede ser negativa.")
    @Column(nullable = false)
    private Integer edad;

    @NotBlank(message = "El género es obligatorio.")
    @Column(nullable = false, length = 20)
    private String genero;

    @NotBlank(message = "La ubicación es obligatoria.")
    @Column(nullable = false, length = 100)
    private String ubicacion;

    @NotBlank(message = "El estado de adopción es obligatorio.")
    @Column(nullable = false, length = 30)
    private String estadoAdopcion;

    @NotBlank(message = "La foto es obligatoria.")
    @Column(nullable = false, length = 255)
    private String fotoUrl;

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String especie, String raza, Integer edad,
                   String genero, String ubicacion, String estadoAdopcion, String fotoUrl) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.genero = genero;
        this.ubicacion = ubicacion;
        this.estadoAdopcion = estadoAdopcion;
        this.fotoUrl = fotoUrl;
    }

    public Mascota(String nombre, String especie, String raza, Integer edad,
               String genero, String ubicacion, String estadoAdopcion, String fotoUrl) {
    this.nombre = nombre;
    this.especie = especie;
    this.raza = raza;
    this.edad = edad;
    this.genero = genero;
    this.ubicacion = ubicacion;
    this.estadoAdopcion = estadoAdopcion;
    this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public void setEstadoAdopcion(String estadoAdopcion) {
        this.estadoAdopcion = estadoAdopcion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
