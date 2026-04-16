package com.tryplace.tryplace.dto

import java.util.UUID

// aqui vai ser a resposta da chamada

data class LocadorDto(
    val id: UUID,
    val nome: String,
    val nomeImobiliaria: String?,
    val email: String,
    val telefone: String,
    val cnpj: String
)
