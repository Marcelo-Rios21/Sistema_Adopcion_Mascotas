package com.adopcion.mascotas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopcion.mascotas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}