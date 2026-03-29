package com.veterinaria.veterinaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.veterinaria.veterinaria.security.CustomUserDetailsService;
import com.veterinaria.veterinaria.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http
                // Temporalmente se mantiene deshabilitado para no romper
                // los formularios actuales. Lo endureceremos cuando rehagamos las vistas.
                .csrf(csrf -> csrf.disable())

                // Permitimos sesión para Thymeleaf/login web y JWT para API.
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .authorizeHttpRequests(auth -> auth
                    // =========================
                    // RUTAS WEB PÚBLICAS
                    // =========================
                    .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()

                    // RUTAS WEB PÚBLICAS FUTURAS
                    .requestMatchers("/catalogo", "/catalogo/buscar").permitAll()

                    // =========================
                    // API PÚBLICA
                    // =========================
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()

                    // Compatibilidad temporal con el proyecto actual:
                    // listar pacientes queda público como futuro catálogo.
                    .requestMatchers(HttpMethod.GET, "/api/pacientes").permitAll()

                    // =========================
                    // API PRIVADA ACTUAL
                    // =========================
                    .requestMatchers(HttpMethod.POST, "/api/pacientes").authenticated()
                    .requestMatchers("/api/citas/**").authenticated()

                    // =========================
                    // API PRIVADA FUTURA
                    // =========================
                    .requestMatchers("/api/private/**").authenticated()

                    // =========================
                    // RUTAS WEB PRIVADAS ACTUALES
                    // =========================
                    .requestMatchers("/dashboard", "/pacientes/**", "/citas/**").authenticated()

                    // RUTAS WEB PRIVADAS FUTURAS
                    .requestMatchers("/admin/**").authenticated()

                    // Todo lo demás, privado por defecto
                    .anyRequest().authenticated()
                )

                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard", true)
                    .permitAll()
                )

                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                )

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();

        } catch (Exception e) {
            throw new IllegalStateException("Error al configurar Spring Security.", e);
        }
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        try {
            return configuration.getAuthenticationManager();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo crear AuthenticationManager.", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}