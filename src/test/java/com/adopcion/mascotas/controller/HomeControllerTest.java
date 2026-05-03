package com.adopcion.mascotas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class HomeControllerTest {

    private final HomeController homeController = new HomeController();

    @Test
    void testInicioRetornaVistaIndex() {
        String vista = homeController.inicio();

        assertEquals("index", vista);
    }

    @Test
    void testLoginRetornaVistaLogin() {
        String vista = homeController.login();

        assertEquals("login", vista);
    }

    @Test
    void testDashboardRetornaVistaDashboard() {
        String vista = homeController.dashboard();

        assertEquals("dashboard", vista);
    }
}