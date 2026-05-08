package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.time.LocalDate
import java.util.*

class AutenticacaoServiceTest {

    private lateinit var repository: UsuarioRepository
    private lateinit var service: AutenticacaoService

    @BeforeEach
    fun setup() {
        repository = Mockito.mock(UsuarioRepository::class.java)
        service = AutenticacaoService(repository)
    }

    @Test
    fun `deve retornar user details quando email for encontrado`() {

        val email = "teste@email.com"

        val usuarioMock = UsuarioModel(
            id = UUID.randomUUID(),
            nomeCompleto = "João",
            email = email,
            cpfCnpj = "12345678901",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "hash"
        )

        Mockito.`when`(repository.findByEmail(email))
            .thenReturn(usuarioMock)

        val result = service.loadUserByUsername(email)

        assertNotNull(result)
        assertEquals(email, result.username)
    }

    @Test
    fun `deve lancar UsernameNotFoundException quando email nao existir`() {

        val email = "inexistente@email.com"

        Mockito.`when`(repository.findByEmail(email))
            .thenReturn(null)

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            service.loadUserByUsername(email)
        }

        assertTrue(exception.message!!.contains("Usuário não encontrado"))
    }
}