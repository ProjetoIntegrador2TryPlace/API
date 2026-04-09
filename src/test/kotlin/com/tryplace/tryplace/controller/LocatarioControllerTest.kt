package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LocatarioDto
import com.tryplace.tryplace.dto.LocatarioRequest
import com.tryplace.tryplace.service.LocatarioService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

class LocatarioControllerTest {

    private lateinit var service: LocatarioService
    private lateinit var controller: LocatarioController

    @BeforeEach
    fun setup() {
        service = Mockito.mock(LocatarioService::class.java)
        controller = LocatarioController(service)
    }

    @Test
    fun `deve retornar locatario ao buscar`() {
        val dto = LocatarioDto(
            id = UUID.randomUUID(),
            nome = "João",
            email = "email@test.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01"
        )

        Mockito.`when`(service.buscarLocatario("João")).thenReturn(dto)

        val result = controller.listarLocatario("João")

        assertEquals("João", result?.nome)
    }

    @Test
    fun `deve criar locatario`() {
        val request = LocatarioRequest(
            nome = "João",
            email = "email@test.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "123456"
        )

        val dto = LocatarioDto(
            id = UUID.randomUUID(),
            nome = "João",
            email = "email@test.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01"
        )

        Mockito.`when`(service.criarLocatario(request)).thenReturn(dto)

        val result = controller.criarLocatario(request)

        assertEquals(dto.id, result.id)
    }
}