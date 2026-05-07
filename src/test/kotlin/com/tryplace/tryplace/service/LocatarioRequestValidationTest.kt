package com.tryplace.tryplace.dto

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocatarioRequestValidationTest {

    private lateinit var validator: Validator

    @BeforeEach
    fun setup() {
        // Instancia o validador padrão do Spring/Hibernate para testar o DTO isoladamente
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun `deve passar sem erros quando todos os dados forem validos`() {
        val request = LocatarioRequest(
            nome = "João da Silva",
            email = "joao@email.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "senhaSegura123"
        )

        val violacoes = validator.validate(request)
        assertTrue(violacoes.isEmpty(), "Não deve haver violações quando os dados estão corretos")
    }

    // 🔥 Referência: CT-14
    @Test
    fun `deve acusar erro de validacao quando email for em formato invalido`() {
        val request = LocatarioRequest(
            nome = "João",
            email = "email_invalido.com", // Formato sem @
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "senhaSegura"
        )

        val violacoes = validator.validate(request)
        
        assertFalse(violacoes.isEmpty(), "Deve barrar e-mail inválido")
        assertTrue(violacoes.any { it.propertyPath.toString() == "email" })
    }

    // 🔥 Referência: CT-15
    @Test
    fun `deve acusar erro de validacao quando senha for muito curta`() {
        val request = LocatarioRequest(
            nome = "João",
            email = "joao@email.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "123" // Senha fraca/curta
        )

        val violacoes = validator.validate(request)

        assertFalse(violacoes.isEmpty(), "Deve barrar senha com menos de 6 caracteres")
        assertTrue(violacoes.any { it.propertyPath.toString() == "senha" })
    }

    // 🔥 Referência: CT-16
    @Test
    fun `deve acusar erro de validacao quando campos obrigatorios estiverem em branco`() {
        val request = LocatarioRequest(
            nome = "   ", // Nome em branco
            email = "joao@email.com",
            telefone = " ", // Telefone em branco
            dataDeNascimento = "1990-01-01",
            senha = "senhaSegura"
        )

        val violacoes = validator.validate(request)

        assertFalse(violacoes.isEmpty(), "Deve barrar campos obrigatórios vazios")
        assertTrue(violacoes.any { it.propertyPath.toString() == "nome" })
        assertTrue(violacoes.any { it.propertyPath.toString() == "telefone" })
    }

    // 🔥 Referência: CT-17
    @Test
    fun `deve acusar erro de validacao quando nome ultrapassar o limite maximo de caracteres`() {
        val nomeGigante = "A".repeat(256) // 256 caracteres
        
        val request = LocatarioRequest(
            nome = nomeGigante,
            email = "joao@email.com",
            telefone = "11999999999",
            dataDeNascimento = "1990-01-01",
            senha = "senhaSegura"
        )

        val violacoes = validator.validate(request)

        assertFalse(violacoes.isEmpty(), "Deve barrar nome com mais de 255 caracteres")
        assertTrue(violacoes.any { it.propertyPath.toString() == "nome" })
    }
}