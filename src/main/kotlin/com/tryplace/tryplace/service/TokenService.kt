package com.tryplace.tryplace.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.tryplace.tryplace.model.LocatarioModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


@Service
class TokenService {

    @Value("\${api.security.token.secret}")
    private lateinit var secret: String

    fun gerarToken(usuario: LocatarioModel): String {
        return try {
            val algoritmo = Algorithm.HMAC256(secret)
            JWT.create()
                .withIssuer("API TryPlace")
                .withSubject(usuario.email)
                .withExpiresAt(dataExpiracao())
                .sign(algoritmo)
        } catch (exception: JWTCreationException) {
            throw RuntimeException("Erro ao gerar token jwt", exception)
        }
    }

    private fun dataExpiracao(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }

    fun getSubject(tokenJWT: String): String {
        return try {
            val algoritmo = Algorithm.HMAC256(secret)
            JWT.require(algoritmo)
                .withIssuer("API TryPlace")
                .build()
                .verify(tokenJWT)
                .subject
        } catch (exception: Exception) {
            throw RuntimeException("Token JWT inválido ou expirado!")
        }
    }



}