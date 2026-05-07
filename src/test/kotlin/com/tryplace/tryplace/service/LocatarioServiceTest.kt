package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.LocatarioRequest
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.repository.LocatarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

class LocatarioServiceTest {

    private lateinit var repository: LocatarioRepository
    private lateinit var passwordEncoder: BCryptPasswordEncoder
    private lateinit var service: LocatarioService

    @BeforeEach
    fun setup() {
        repository = Mockito.mock(LocatarioRepository::class.java)
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)
        service = LocatarioService(repository, passwordEncoder)
    }

    @Test
    fun `deve criar locatario com senha criptografada e remover espacos extras (trim)`() {
        // Request com espaços em branco sobrando de propósito
        val request = LocatarioRequest(
            nome = " João ",
            email = " joao@teste.com ",
            telefone = " 11999999999 ",
            dataDeNascimento = " 1990-01-01 ",
            senha = "senha_plana"
        )

        val idGerado = UUID.randomUUID()
        val locatarioSalvoMock = LocatarioModel(
            id = idGerado,
            nome = "João",
            email = "joao@teste.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "senha_criptografada"
        )

        // Simula a criptografia
        Mockito.`when`(passwordEncoder.encode("senha_plana")).thenReturn("senha_criptografada")
        
        // Simula o salvamento no banco
        Mockito.`when`(repository.save(Mockito.any())).thenReturn(locatarioSalvoMock)

        val result = service.criarLocatario(request)

        // Verificações
        assertEquals(idGerado, result.id)
        assertEquals("João", result.nome) // Garante que o .trim() funcionou
        assertEquals("joao@teste.com", result.email)
        
        // Garante que o encoder foi chamado exatamente com a senha da request
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("senha_plana")
    }

    @Test
    fun `deve retornar LocatarioDto quando buscarLocatario encontrar nome no banco`() {
        val nome = "Maria"
        val locatarioMock = LocatarioModel(
            id = UUID.randomUUID(),
            nome = nome,
            email = "maria@teste.com",
            telefone = "11988888888",
            dataDeNascimento = "1995-05-05",
            senha = "hash"
        )

        Mockito.`when`(repository.findByNome(nome)).thenReturn(locatarioMock)

        val result = service.buscarLocatario(nome)

        assertNotNull(result)
        assertEquals(nome, result?.nome)
        assertEquals("maria@teste.com", result?.email)
    }

    @Test
    fun `deve retornar null quando buscarLocatario nao encontrar ninguem`() {
        val nomeInexistente = "Fantasma"
        
        Mockito.`when`(repository.findByNome(nomeInexistente)).thenReturn(null)

        val result = service.buscarLocatario(nomeInexistente)

        assertNull(result)
    }
}