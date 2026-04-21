package com.tryplace.tryplace.dto

import java.time.LocalDate
import java.util.UUID

data class UsuarioDto(
    val id: UUID?,
    val nomeCompleto: String,
    val email: String,
    val cpfCnpj: String,
    val telefone: String,
    val dataDeNascimento: LocalDate,
    val isMaiorDeIdade: Boolean,

    val nomeEmpresa: String?,
    val cursoPeriodo: String?,
    val interesseDividir: Boolean

    )
