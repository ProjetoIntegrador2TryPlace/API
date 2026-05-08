package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.UsuarioRequest
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate
import java.util.*

class UsuarioServiceTest {

    private lateinit var repository: UsuarioRepository
    private lateinit var passwordEncoder: BCryptPasswordEncoder
    private lateinit var service: UsuarioService

    @BeforeEach
    fun setup() {
        repository = Mockito.mock(UsuarioRepository::class.java)
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)
        service = UsuarioService(repository, passwordEncoder)
    }

    @Test
    fun `deve criar usuario com senha criptografada`() {

        val request = UsuarioRequest(
            nomeCompleto = "João da Silva",
            email = "joao@teste.com",
            cpfCNPJ = "12345678901",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "senha_plana"
        )

        val usuarioSalvo = UsuarioModel(
            id = UUID.randomUUID(),
            nomeCompleto = "João da Silva",
            email = "joao@teste.com",
            cpfCnpj = "12345678901",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "senha_criptografada"
        )

        Mockito.`when`(passwordEncoder.encode("senha_plana"))
            .thenReturn("senha_criptografada")

        Mockito.`when`(repository.save(Mockito.any()))
            .thenReturn(usuarioSalvo)

        Mockito.`when`(repository.existsByEmail(request.email))
            .thenReturn(false)

        Mockito.`when`(repository.existsByCpfCnpj(request.cpfCNPJ))
            .thenReturn(false)

        val result = service.cadastrarUsuario(request, "127.0.0.1")

        assertEquals("João da Silva", result.nomeCompleto)
        assertEquals("joao@teste.com", result.email)

        Mockito.verify(passwordEncoder, Mockito.times(1))
            .encode("senha_plana")
    }
}