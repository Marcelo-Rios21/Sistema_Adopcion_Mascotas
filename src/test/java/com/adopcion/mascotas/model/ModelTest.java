package com.adopcion.mascotas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class ModelTest {

    @Test
    void testMascotaConstructorConId() {
        Mascota mascota = new Mascota(
                1L,
                "Firulais",
                "Perro",
                "Labrador",
                5,
                "Macho",
                "Santiago",
                "Disponible",
                "firulais.jpg"
        );

        assertEquals(1L, mascota.getId());
        assertEquals("Firulais", mascota.getNombre());
        assertEquals("Perro", mascota.getEspecie());
        assertEquals("Labrador", mascota.getRaza());
        assertEquals(5, mascota.getEdad());
        assertEquals("Macho", mascota.getGenero());
        assertEquals("Santiago", mascota.getUbicacion());
        assertEquals("Disponible", mascota.getEstadoAdopcion());
        assertEquals("firulais.jpg", mascota.getFotoUrl());
    }

    @Test
    void testMascotaConstructorSinId() {
        Mascota mascota = new Mascota(
                "Michi",
                "Gato",
                "Siames",
                3,
                "Hembra",
                "Puente Alto",
                "Disponible",
                "michi.jpg"
        );

        assertEquals("Michi", mascota.getNombre());
        assertEquals("Gato", mascota.getEspecie());
        assertEquals("Siames", mascota.getRaza());
        assertEquals(3, mascota.getEdad());
        assertEquals("Hembra", mascota.getGenero());
        assertEquals("Puente Alto", mascota.getUbicacion());
        assertEquals("Disponible", mascota.getEstadoAdopcion());
        assertEquals("michi.jpg", mascota.getFotoUrl());
    }

    @Test
    void testMascotaSettersAndGetters() {
        Mascota mascota = new Mascota();

        mascota.setId(10L);
        mascota.setNombre("Luna");
        mascota.setEspecie("Perro");
        mascota.setRaza("Mestiza");
        mascota.setEdad(2);
        mascota.setGenero("Hembra");
        mascota.setUbicacion("La Florida");
        mascota.setEstadoAdopcion("En proceso");
        mascota.setFotoUrl("luna.jpg");

        assertEquals(10L, mascota.getId());
        assertEquals("Luna", mascota.getNombre());
        assertEquals("Perro", mascota.getEspecie());
        assertEquals("Mestiza", mascota.getRaza());
        assertEquals(2, mascota.getEdad());
        assertEquals("Hembra", mascota.getGenero());
        assertEquals("La Florida", mascota.getUbicacion());
        assertEquals("En proceso", mascota.getEstadoAdopcion());
        assertEquals("luna.jpg", mascota.getFotoUrl());
    }

    @Test
    void testUsuarioConstructorConParametros() {
        Usuario usuario = new Usuario(
                1L,
                "admin",
                "1234",
                Rol.ADMIN
        );

        assertEquals(1L, usuario.getId());
        assertEquals("admin", usuario.getUsername());
        assertEquals("1234", usuario.getPassword());
        assertEquals(Rol.ADMIN, usuario.getRol());
    }

    @Test
    void testUsuarioSettersAndGetters() {
        Usuario usuario = new Usuario();

        usuario.setId(2L);
        usuario.setUsername("gestor");
        usuario.setPassword("gestor123");
        usuario.setRol(Rol.GESTOR);

        assertEquals(2L, usuario.getId());
        assertEquals("gestor", usuario.getUsername());
        assertEquals("gestor123", usuario.getPassword());
        assertEquals(Rol.GESTOR, usuario.getRol());
    }

    @Test
    void testRolValues() {
        assertEquals(Rol.ADMIN, Rol.valueOf("ADMIN"));
        assertEquals(Rol.GESTOR, Rol.valueOf("GESTOR"));
        assertEquals(Rol.OPERADOR, Rol.valueOf("OPERADOR"));
    }
}