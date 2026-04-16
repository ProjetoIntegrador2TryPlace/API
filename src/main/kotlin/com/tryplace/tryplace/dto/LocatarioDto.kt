package com.tryplace.tryplace.dto

import java.util.UUID

data class LocatarioDto(
    val id: UUID,
    val nome: String,
    val email: String,
    val telefone: String,
    val dataDeNascimento: String
)
