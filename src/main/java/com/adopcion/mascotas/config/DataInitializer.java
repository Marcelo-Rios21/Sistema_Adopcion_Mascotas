package com.adopcion.mascotas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.adopcion.mascotas.model.Mascota;
import com.adopcion.mascotas.model.Rol;
import com.adopcion.mascotas.model.Usuario;
import com.adopcion.mascotas.repository.MascotaRepository;
import com.adopcion.mascotas.repository.UsuarioRepository;


@Configuration
public class DataInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initDatos(
        UsuarioRepository usuarioRepository,
        MascotaRepository mascotaRepository,
        PasswordEncoder passwordEncoder,
        @Value("${app.seed.admin-password:}") String adminPassword,
        @Value("${app.seed.gestor-password:}") String gestorPassword,
        @Value("${app.seed.operador-password:}") String operadorPassword) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                if (tieneTexto(adminPassword) && tieneTexto(gestorPassword) && tieneTexto(operadorPassword)) {
                        usuarioRepository.save(new Usuario(
                                null,
                                "admin",
                                passwordEncoder.encode(adminPassword),
                                Rol.ADMIN
                        ));

                        usuarioRepository.save(new Usuario(
                                null,
                                "gestor",
                                passwordEncoder.encode(gestorPassword),
                                Rol.GESTOR
                        ));

                        usuarioRepository.save(new Usuario(
                                null,
                                "operador",
                                passwordEncoder.encode(operadorPassword),
                                Rol.OPERADOR
                        ));
                } else {
                        LOGGER.info("Usuarios iniciales no creados: no se configuraron contraseñas por variables de entorno.");
                }
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

        private boolean tieneTexto(String valor) {
                return valor != null && !valor.isBlank();
        }
}