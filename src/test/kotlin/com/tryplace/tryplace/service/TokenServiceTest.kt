package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.LocatarioModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.util.*

class TokenServiceTest {

    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setup() {
        tokenService = TokenService()

        // injeta o secret manualmente
        ReflectionTestUtils.setField(tokenService, "secret", "segredo-teste")
    }

    @Test
    fun `deve gerar token valido`() {
        val usuario = LocatarioModel(
            id = UUID.randomUUID(),
            nome = "João",
            email = "joao@email.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "hash"
        )

        val token = tokenService.gerarToken(usuario)

        assertNotNull(token)
        assertTrue(token.isNotEmpty())
    }

    @Test
    fun `deve extrair subject corretamente`() {
        val usuario = LocatarioModel(
            id = UUID.randomUUID(),
            nome = "Maria",
            email = "maria@email.com",
            telefone = "11988888888",
            dataDeNascimento = "1995-05-05",
            senha = "hash"
        )

        val token = tokenService.gerarToken(usuario)

        val subject = tokenService.getSubject(token)

        assertEquals("maria@email.com", subject)
    }

    @Test
    fun `deve lançar excecao para token invalido`() {
        assertThrows(RuntimeException::class.java) {
            tokenService.getSubject("token_invalido")
        }
    }
}