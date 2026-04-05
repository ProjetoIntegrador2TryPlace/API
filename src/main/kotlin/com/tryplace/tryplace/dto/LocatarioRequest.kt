package com.tryplace.tryplace.dto


// aqui é o que essa estrutura vai esperar
data class LocatarioRequest(
    val nome: String,
    val email: String,
    val telefone: String,
    val dataDeNascimento: String
)

