package com.adopcion.mascotas.security;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

    private JwtService jwtService;

    private final String secret = "clave_super_secreta_para_jwt_adopcion_mascotas_2026";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L);
    }

    @Test
    void testGenerateTokenRetornaTokenNoNulo() {
        UserDetails userDetails = User
                .withUsername("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void testExtractUsernameDesdeTokenValido() {
        UserDetails userDetails = User
                .withUsername("gestor")
                .password("1234")
                .roles("GESTOR")
                .build();

        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals("gestor", username);
    }

    @Test
    void testIsTokenValidConTokenCorrecto() {
        UserDetails userDetails = User
                .withUsername("operador")
                .password("1234")
                .roles("OPERADOR")
                .build();

        String token = jwtService.generateToken(userDetails);

        boolean valido = jwtService.isTokenValid(token, userDetails);

        assertTrue(valido);
    }

    @Test
    void testIsTokenValidConUsernameDistintoDebeRetornarFalse() {
        UserDetails usuarioOriginal = User
                .withUsername("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        UserDetails otroUsuario = User
                .withUsername("gestor")
                .password("1234")
                .roles("GESTOR")
                .build();

        String token = jwtService.generateToken(usuarioOriginal);

        boolean valido = jwtService.isTokenValid(token, otroUsuario);

        assertFalse(valido);
    }

    @Test
    void testIsTokenValidConTokenInvalidoDebeRetornarFalse() {
        UserDetails userDetails = User
                .withUsername("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        boolean valido = jwtService.isTokenValid("token-invalido", userDetails);

        assertFalse(valido);
    }

    @Test
    void testIsTokenValidConTokenExpiradoDebeRetornarFalse() {
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1000L);

        UserDetails userDetails = User
                .withUsername("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        String tokenExpirado = jwtService.generateToken(userDetails);

        boolean valido = jwtService.isTokenValid(tokenExpirado, userDetails);

        assertFalse(valido);
    }

    @Test
    void testGenerateTokenConUsuarioSinRolesUsaRolPorDefecto() {
        UserDetails userDetailsSinRoles = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public String getPassword() {
                return "1234";
            }

            @Override
            public String getUsername() {
                return "sinroles";
            }
        };

        String token = jwtService.generateToken(userDetailsSinRoles);

        assertNotNull(token);
        assertEquals("sinroles", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, userDetailsSinRoles));
    }
}