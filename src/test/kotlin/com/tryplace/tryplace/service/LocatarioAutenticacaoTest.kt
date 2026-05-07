package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.repository.LocatarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*

class LocatarioAutenticacaoTest {

    private lateinit var repository: LocatarioRepository
    private lateinit var service: LocatarioAutenticacao

    @BeforeEach
    fun setup() {
        repository = Mockito.mock(LocatarioRepository::class.java)
        service = LocatarioAutenticacao(repository)
    }

    @Test
    fun `deve retornar user details quando email for encontrado`() {
        val email = "teste@email.com"
        val usuarioMock = LocatarioModel(
            id = UUID.randomUUID(),
            nome = "João",
            email = email,
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "hash"
        )

        // Simula o banco de dados retornando o usuário
        Mockito.`when`(repository.findByEmail(email)).thenReturn(usuarioMock)

        val result = service.loadUserByUsername(email)

        assertNotNull(result)
        assertEquals(email, result.username)
    }

    @Test
    fun `deve lancar UsernameNotFoundException quando email nao existir`() {
        val email = "inexistente@email.com"

        // Simula o banco não encontrando ninguém (retorna null)
        Mockito.`when`(repository.findByEmail(email)).thenReturn(null)

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            service.loadUserByUsername(email)
        }

        assertTrue(exception.message!!.contains("Usuário não encontrado"))
    }
}