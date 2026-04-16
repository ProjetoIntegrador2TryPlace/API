package com.tryplace.tryplace.dto

import java.util.UUID

data class LocatarioRequest(
    val nome: String,
    val email: String,
    val telefone: String,
    val dataDeNascimento: String,
    val senha: String
)
