package com.tryplace.tryplace.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val securityFilter: SecurityFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/api/locador", "/api/locador/**").permitAll()
                auth.requestMatchers("/api/loginLocador", "/api/loginLocador/**").permitAll()
                auth.requestMatchers("/error").permitAll()
                auth.requestMatchers("/api/locatario/**").permitAll()
                auth.requestMatchers("/api/login", "/api/login/**").permitAll()
                auth.requestMatchers("/login", "/login/**").permitAll() // ← linha adicionada
                auth.requestMatchers("/api/imovel", "/api/imovel/**").permitAll()
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
    @Bean
    fun passwordEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun autenticacaoManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }
}