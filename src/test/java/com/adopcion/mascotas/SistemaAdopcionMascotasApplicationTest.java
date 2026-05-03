package com.adopcion.mascotas;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SistemaAdopcionMascotasApplicationTest {

    @Test
    void contextLoads() {
    }

    @Test
    void mainEjecutaSpringApplicationRun() {
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            String[] args = {};

            SistemaAdopcionMascotasApplication.main(args);

            springApplication.verify(() ->
                    SpringApplication.run(SistemaAdopcionMascotasApplication.class, args)
            );
        }
    }
}
