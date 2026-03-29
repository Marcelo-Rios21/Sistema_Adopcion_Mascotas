package com.veterinaria.veterinaria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veterinaria.veterinaria.model.Mascota;
import com.veterinaria.veterinaria.repository.MascotaRepository;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
    }

    public List<Mascota> buscarPorEspecie(String especie) {
        return mascotaRepository.findByEspecieContainingIgnoreCase(especie);
    }

    public List<Mascota> buscarPorUbicacion(String ubicacion) {
        return mascotaRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }

    public List<Mascota> buscarPorGenero(String genero) {
        return mascotaRepository.findByGeneroContainingIgnoreCase(genero);
    }

    public List<Mascota> buscarPorEdad(Integer edad) {
        return mascotaRepository.findByEdad(edad);
    }
}
