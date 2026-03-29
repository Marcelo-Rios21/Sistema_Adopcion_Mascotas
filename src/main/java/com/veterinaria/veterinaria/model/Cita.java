package com.veterinaria.veterinaria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del paciente es obligatorio.")
    @Column(nullable = false, length = 100)
    private String nombrePaciente;

    @NotBlank(message = "La fecha es obligatoria.")
    @Column(nullable = false, length = 20)
    private String fecha;

    @NotBlank(message = "La hora es obligatoria.")
    @Column(nullable = false, length = 20)
    private String hora;

    @NotBlank(message = "El motivo es obligatorio.")
    @Column(nullable = false, length = 150)
    private String motivo;

    @NotBlank(message = "El veterinario asignado es obligatorio.")
    @Column(nullable = false, length = 100)
    private String veterinarioAsignado;

    public Cita() {
    }

    public Cita(Long id, String nombrePaciente, String fecha, String hora, String motivo, String veterinarioAsignado) {
        this.id = id;
        this.nombrePaciente = nombrePaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.veterinarioAsignado = veterinarioAsignado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getVeterinarioAsignado() {
        return veterinarioAsignado;
    }

    public void setVeterinarioAsignado(String veterinarioAsignado) {
        this.veterinarioAsignado = veterinarioAsignado;
    }

}
