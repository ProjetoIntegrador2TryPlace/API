package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.UsuarioRequest
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UsuarioRequestValidationTest {

    private lateinit var validator: Validator

    @BeforeEach
    fun setup() {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun `deve passar sem erros quando todos os dados forem validos`() {

        val request = UsuarioRequest(
            nomeCompleto = "João da Silva",
            email = "joao@email.com",
            cpfCNPJ = "52998224725",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "senhaSegura123"
        )

        val violacoes = validator.validate(request)

        assertTrue(violacoes.isEmpty())
    }

    @Test
    fun `deve acusar erro de validacao quando email for invalido`() {

        val request = UsuarioRequest(
            nomeCompleto = "João da Silva",
            email = "email_invalido",
            cpfCNPJ = "52998224725",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "senhaSegura123"
        )

        val violacoes = validator.validate(request)

        assertFalse(violacoes.isEmpty())

        assertTrue(
            violacoes.any {
                it.propertyPath.toString() == "email"
            }
        )
    }

    @Test
    fun `deve acusar erro quando senha for curta`() {

        val request = UsuarioRequest(
            nomeCompleto = "João da Silva",
            email = "joao@email.com",
            cpfCNPJ = "52998224725",
            telefone = "(88) 99999-9999",
            dataDeNascimento = LocalDate.of(1990, 1, 1),
            senha = "123"
        )

        val violacoes = validator.validate(request)

        assertFalse(violacoes.isEmpty())

        assertTrue(
            violacoes.any {
                it.propertyPath.toString() == "senha"
            }
        )
    }
}