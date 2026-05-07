package com.tryplace.tryplace.dto

import com.tryplace.tryplace.validation.CpfOuCnpj
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UsuarioRequest(
    @field:NotBlank(message = "O nome completo é obrigatório")
    @field:Size(min = 6, max = 150, message = "O nome deve ter entre 6 caracteres e 150 caracteres")
    val nomeCompleto: String,

    @field:NotBlank(message = "O email é obrigatório")
    @field:Email(message = "Email inválido")
    val email: String,

    @field:NotBlank(message = "CPF ou CNPJ é obrigatório! ")
    @field:Pattern(
        regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)|(^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$)|(^\\d{11}$)|(^\\d{14}$)",
        message = "Formato de CPF ou CNPJ inválido"
    )
    @field:CpfOuCnpj
    val cpfCNPJ: String,
    @field:Pattern(
        regexp = "^\\(\\d{2}\\)\\s\\d{5}-\\d{4}$",
        message = "Telefone deve seguir o padrão (88) 99999-9999"
    )
    @field:NotBlank(message = "O telefone é obrigatório")
    val telefone: String,
    @field:NotNull(message = "Data de Nascimento é obrigatória")
    val dataDeNascimento: LocalDate,
    @field:Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    val senha: String,


    // so vai ser utilizado se for menor de 18 anos
    val nomeResponsavel: String? = null,
    val cpfResponsavel: String? = null
)
