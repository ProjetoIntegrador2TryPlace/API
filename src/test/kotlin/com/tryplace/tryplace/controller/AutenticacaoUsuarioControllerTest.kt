package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LoginUsuarioRequest
import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.service.TokenService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import java.time.LocalDate
import java.util.*

class AutenticacaoUsuarioControllerTest {

    private lateinit var manager: AuthenticationManager
    private lateinit var tokenService: TokenService
    private lateinit var controller: AutenticacaoUsuarioController

    @BeforeEach
    fun setup() {

        manager = Mockito.mock(AuthenticationManager::class.java)

        tokenService = Mockito.mock(TokenService::class.java)

        controller = AutenticacaoUsuarioController(
            manager,
            tokenService
        )
    }

    @Test
    fun `deve retornar token quando login for valido`() {

        val request = LoginUsuarioRequest(
            email = "email@test.com",
            senha = "123"
        )

        val usuario = UsuarioModel(
            id = UUID.randomUUID(),
            nomeCompleto = "João",
            email = "email@test.com",
            cpfCnpj = "12345678901",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "hash"
        )

        val authentication = Mockito.mock(Authentication::class.java)

        Mockito.`when`(authentication.principal)
            .thenReturn(usuario)

        Mockito.`when`(manager.authenticate(Mockito.any()))
            .thenReturn(authentication)

        Mockito.`when`(tokenService.gerarToken(usuario))
            .thenReturn("token_jwt")

        val response: TokenDto =
            controller.efetuarLogin(request)

        assertEquals("token_jwt", response.token)
    }
}   