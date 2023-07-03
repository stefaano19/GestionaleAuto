package com.example.gestionaleauto.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/appuntamenti/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/auto/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/caseProduttrici/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/clienti/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/fatture/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/fornitori/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/home/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/ordini/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/preventivi/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/prodotti/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/utenti/**").permitAll()
                    .anyRequest().permitAll();
        return http.build();
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedOrigin(".");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }


}
