package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LoginLocatarioRequest
import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.service.TokenService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication 
import java.util.*

class AutenticacaoLocatarioControllerTest {

    private lateinit var manager: AuthenticationManager
    private lateinit var tokenService: TokenService
    private lateinit var controller: AutenticacaoLocatarioController

    @BeforeEach
    fun setup() {
        manager = Mockito.mock(AuthenticationManager::class.java)
        tokenService = Mockito.mock(TokenService::class.java)
        controller = AutenticacaoLocatarioController(manager, tokenService)
    }

    @Test
    fun `deve retornar token quando login for valido`() {
        val request = LoginLocatarioRequest("email@test.com", "123")

        val usuario = LocatarioModel(
            id = UUID.randomUUID(),
            nome = "João",
            email = "email@test.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "hash"
        )

        val authentication = Mockito.mock(Authentication::class.java)

        Mockito.`when`(authentication.principal).thenReturn(usuario)
        Mockito.`when`(manager.authenticate(Mockito.any())).thenReturn(authentication)
        Mockito.`when`(tokenService.gerarToken(usuario)).thenReturn("token_jwt")

        val response: TokenDto = controller.efetuarLogin(request)

        assertEquals("token_jwt", response.token)
    }
}