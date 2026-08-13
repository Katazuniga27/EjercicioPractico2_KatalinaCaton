package com.MediCare.demo.config;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            String destino = "/";
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String rol = authority.getAuthority();
                if (rol.equals("ROLE_ADMIN")) {
                    destino = "/usuario/listado";
                    break;
                } else if (rol.equals("ROLE_MEDICO")) {
                    destino = "/cita/listado";
                    break;
                } else if (rol.equals("ROLE_PACIENTE")) {
                    destino = "/cita/listado";
                    break;
                }
            }
            response.sendRedirect(request.getContextPath() + destino);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Recursos publicos: pagina principal, login y librerias estaticas
                .requestMatchers("/", "/login", "/acceso-denegado",
                        "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
                // Roles: solo el ADMIN administra los roles
                .requestMatchers("/rol/**").hasRole("ADMIN")
                // Usuarios: solo el ADMIN administra los usuarios
                .requestMatchers("/usuario/**").hasRole("ADMIN")
                // Citas medicas: ADMIN y MEDICO pueden crear/editar/eliminar
                .requestMatchers("/cita/listado", "/cita/ver/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                .requestMatchers("/cita/**").hasAnyRole("ADMIN", "MEDICO")
                // Consultas avanzadas: cualquier usuario autenticado
                .requestMatchers("/consultas/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler())
                .failureUrl("/login?error=true")
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        http.exceptionHandling(exceptions -> exceptions
                .accessDeniedPage("/acceso-denegado")
        );

        http.requestCache(cache -> cache.disable());

        return http.build();
    }
}
