package com.veterinaria.veterinaria.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.veterinaria.veterinaria.model.Mascota;
import com.veterinaria.veterinaria.model.Rol;
import com.veterinaria.veterinaria.model.Usuario;
import com.veterinaria.veterinaria.repository.MascotaRepository;
import com.veterinaria.veterinaria.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatos(UsuarioRepository usuarioRepository,
                                       MascotaRepository mascotaRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario(
                        null,
                        "admin",
                        passwordEncoder.encode("Admin1234#"),
                        Rol.ADMIN
                ));

                usuarioRepository.save(new Usuario(
                        null,
                        "gestor",
                        passwordEncoder.encode("Gestor1234#"),
                        Rol.GESTOR
                ));

                usuarioRepository.save(new Usuario(
                        null,
                        "operador",
                        passwordEncoder.encode("Operador1234#"),
                        Rol.OPERADOR
                ));
            }

            if (mascotaRepository.count() == 0) {
                mascotaRepository.save(new Mascota(
                        "Luna", "Perro", "Mestiza", 3,
                        "Hembra", "Puente Alto", "Disponible",
                        "https://placehold.co/300x200?text=Luna"
                ));

                mascotaRepository.save(new Mascota(
                        "Simba", "Gato", "Común europeo", 2,
                        "Macho", "La Florida", "Disponible",
                        "https://placehold.co/300x200?text=Simba"
                ));

                mascotaRepository.save(new Mascota(
                        "Milo", "Perro", "Poodle", 5,
                        "Macho", "Santiago Centro", "En evaluación",
                        "https://placehold.co/300x200?text=Milo"
                ));
            }
        };
    }
}