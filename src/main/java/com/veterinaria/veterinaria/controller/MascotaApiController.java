package com.veterinaria.veterinaria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.veterinaria.model.Mascota;
import com.veterinaria.veterinaria.service.MascotaService;

import jakarta.validation.Valid;

@RestController
public class MascotaApiController {

    private final MascotaService mascotaService;

    public MascotaApiController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping("/api/public/mascotas")
    public List<Mascota> listarMascotas() {
        return mascotaService.listarTodas();
    }

    @GetMapping("/api/public/mascotas/buscar")
    public List<Mascota> buscarMascotas(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer edad) {

        if (especie != null && !especie.isBlank()) {
            return mascotaService.buscarPorEspecie(especie);
        }

        if (ubicacion != null && !ubicacion.isBlank()) {
            return mascotaService.buscarPorUbicacion(ubicacion);
        }

        if (genero != null && !genero.isBlank()) {
            return mascotaService.buscarPorGenero(genero);
        }

        if (edad != null) {
            return mascotaService.buscarPorEdad(edad);
        }

        return mascotaService.listarTodas();
    }

    @PostMapping("/api/private/mascotas")
    public ResponseEntity<Mascota> crearMascota(@Valid @RequestBody Mascota mascota) {
        Mascota guardada = mascotaService.guardar(mascota);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }
}
