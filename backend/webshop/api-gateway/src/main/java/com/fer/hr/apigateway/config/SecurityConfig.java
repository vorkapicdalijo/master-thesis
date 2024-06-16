package com.fer.hr.apigateway.config;


import com.fer.hr.apigateway.jwt.CustomJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeExchange()
                        .pathMatchers("/api/products/**").permitAll()
                        .pathMatchers("/api/products/add").hasAuthority("ROLE_ADMIN")
                        .pathMatchers("/api/products/update").hasAuthority("ROLE_ADMIN")
                        .pathMatchers("/api/products/delete").hasAuthority("ROLE_ADMIN")
                        .pathMatchers("/api/inventory/**").permitAll()
                        .pathMatchers("/images/**").permitAll()
                        .pathMatchers("/api/selects/**").permitAll()
                        .pathMatchers("/api/order/**").authenticated()
                        .pathMatchers("api/payment/**").authenticated()
                        .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(customJwtConverter())
                        )
                );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> customJwtConverter() {
        return new CustomJwtConverter();
    }
}