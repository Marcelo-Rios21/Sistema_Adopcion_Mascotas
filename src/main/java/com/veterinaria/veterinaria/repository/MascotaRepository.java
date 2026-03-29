package com.veterinaria.veterinaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.veterinaria.model.Mascota;


public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByEspecieContainingIgnoreCase(String especie);
    List<Mascota> findByUbicacionContainingIgnoreCase(String ubicacion);
    List<Mascota> findByGeneroContainingIgnoreCase(String genero);
    List<Mascota> findByEdad(Integer edad);
}