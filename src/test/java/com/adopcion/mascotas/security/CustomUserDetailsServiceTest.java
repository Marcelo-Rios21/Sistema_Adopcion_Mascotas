package com.adopcion.mascotas.security;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.adopcion.mascotas.model.Rol;
import com.adopcion.mascotas.model.Usuario;
import com.adopcion.mascotas.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testLoadUserByUsernameExistente() {
        Usuario usuario = new Usuario(
                1L,
                "admin",
                "password-encriptada",
                Rol.ADMIN
        );

        when(usuarioRepository.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        UserDetails resultado = customUserDetailsService.loadUserByUsername("admin");

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());
        assertEquals("password-encriptada", resultado.getPassword());
        assertTrue(
                resultado.getAuthorities()
                        .stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
        );

        verify(usuarioRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testLoadUserByUsernameGestor() {
        Usuario usuario = new Usuario(
                2L,
                "gestor",
                "password-gestor",
                Rol.GESTOR
        );

        when(usuarioRepository.findByUsername("gestor"))
                .thenReturn(Optional.of(usuario));

        UserDetails resultado = customUserDetailsService.loadUserByUsername("gestor");

        assertNotNull(resultado);
        assertEquals("gestor", resultado.getUsername());
        assertEquals("password-gestor", resultado.getPassword());
        assertTrue(
                resultado.getAuthorities()
                        .stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_GESTOR"))
        );

        verify(usuarioRepository, times(1)).findByUsername("gestor");
    }

    @Test
    void testLoadUserByUsernameNoExistente() {
        when(usuarioRepository.findByUsername("desconocido"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("desconocido")
        );

        assertEquals("Usuario no encontrado: desconocido", exception.getMessage());

        verify(usuarioRepository, times(1)).findByUsername("desconocido");
    }
}