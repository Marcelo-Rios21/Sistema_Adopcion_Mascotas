package com.adopcion.mascotas.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class LoginDtoTest {

    @Test
    void testLoginRequestConstructorConParametros() {
        LoginRequest request = new LoginRequest("admin", "1234");

        assertEquals("admin", request.getUsername());
        assertEquals("1234", request.getPassword());
    }

    @Test
    void testLoginRequestSettersAndGetters() {
        LoginRequest request = new LoginRequest();

        request.setUsername("gestor");
        request.setPassword("gestor123");

        assertEquals("gestor", request.getUsername());
        assertEquals("gestor123", request.getPassword());
    }

    @Test
    void testLoginResponseConstructorConParametros() {
        LoginResponse response = new LoginResponse(
                "token-jwt-prueba",
                "Bearer",
                "admin",
                "ADMIN"
        );

        assertEquals("token-jwt-prueba", response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals("admin", response.getUsername());
        assertEquals("ADMIN", response.getRol());
    }

    @Test
    void testLoginResponseSettersAndGetters() {
        LoginResponse response = new LoginResponse();

        response.setToken("otro-token");
        response.setType("Bearer");
        response.setUsername("operador");
        response.setRol("OPERADOR");

        assertEquals("otro-token", response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals("operador", response.getUsername());
        assertEquals("OPERADOR", response.getRol());
    }
}
