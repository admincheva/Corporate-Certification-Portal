package org.example.corporatecertificationportal.config;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/auth/register"
                        ).permitAll()

                        .anyRequest()

                        .authenticated()

                )

                .formLogin(login -> login

                        .loginProcessingUrl("/login")

                        .successHandler((req, res, auth) -> {

                            res.setStatus(200);

                        })

                        .failureHandler((req, res, ex) -> {

                            res.sendError(401);

                        })

                )

                .logout(logout -> logout

                        .logoutUrl("/auth/logout")

                        .logoutSuccessHandler((req, res, auth) -> {

                            res.setStatus(200);

                        })

                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config =
                new CorsConfiguration();

        config.setAllowedOrigins(
                List.of("http://localhost:4200"));

        config.setAllowedMethods(
                List.of("*"));

        config.setAllowedHeaders(
                List.of("*"));

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                config);

        return source;
    }

}