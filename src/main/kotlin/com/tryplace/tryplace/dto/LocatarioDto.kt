package com.tryplace.tryplace.dto

import java.util.UUID

// aqui vai ser a resposta da chamada, o que retorna pro usuário

data class LocatarioDto(
    val id: UUID,
    val nome: String,
    val email: String,
    val telefone: String,
    val dataDeNascimento: String
)
