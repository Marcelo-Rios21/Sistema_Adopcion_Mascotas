package com.adopcion.mascotas;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.adopcion.mascotas.model.Rol;
import com.adopcion.mascotas.repository.UsuarioRepository;

@SpringBootTest
@TestPropertySource(properties = {
        "app.seed.admin-password=seed_admin_test",
        "app.seed.gestor-password=seed_gestor_test",
        "app.seed.operador-password=seed_operador_test"
})
class SistemaAdopcionMascotasApplicationTest {

     @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void debeCrearUsuariosInicialesCuandoExistenCredencialesSeed() {
        assertTrue(usuarioRepository.findByUsername("admin").isPresent());
        assertTrue(usuarioRepository.findByUsername("gestor").isPresent());
        assertTrue(usuarioRepository.findByUsername("operador").isPresent());

        assertTrue(usuarioRepository.findByUsername("admin")
                .filter(usuario -> usuario.getRol() == Rol.ADMIN)
                .isPresent());

        assertTrue(usuarioRepository.findByUsername("gestor")
                .filter(usuario -> usuario.getRol() == Rol.GESTOR)
                .isPresent());

        assertTrue(usuarioRepository.findByUsername("operador")
                .filter(usuario -> usuario.getRol() == Rol.OPERADOR)
                .isPresent());
    }
}