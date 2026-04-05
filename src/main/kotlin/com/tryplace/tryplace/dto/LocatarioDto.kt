package com.tryplace.tryplace.dto

// aqui vai ser a resposta da chamada

data class LocatarioDto(
    val id: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val dataDeNascimento: String
)
