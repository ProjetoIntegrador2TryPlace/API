package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.UsuarioModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDate
import java.util.*

class TokenServiceTest {

    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setup() {
        tokenService = TokenService()

        ReflectionTestUtils.setField(
            tokenService,
            "secret",
            "segredo-teste"
        )
    }

    @Test
    fun `deve gerar token valido`() {

        val usuario = UsuarioModel(
            id = UUID.randomUUID(),
            nomeCompleto = "João",
            email = "joao@email.com",
            cpfCnpj = "12345678901",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "hash"
        )

        val token = tokenService.gerarToken(usuario)

        assertNotNull(token)
        assertTrue(token.isNotEmpty())
    }

    @Test
    fun `deve extrair subject corretamente`() {

        val usuario = UsuarioModel(
            id = UUID.randomUUID(),
            nomeCompleto = "Maria",
            email = "maria@email.com",
            cpfCnpj = "12345678901",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1995, 5, 5),
            senha = "hash"
        )

        val token = tokenService.gerarToken(usuario)

        val subject = tokenService.getSubject(token)

        assertEquals("maria@email.com", subject)
    }

    @Test
    fun `deve lancar excecao para token invalido`() {

        assertThrows(RuntimeException::class.java) {
            tokenService.getSubject("token_invalido")
        }
    }
}