package com.tryplace.tryplace.dto


// aqui é o que essa estrutura vai esperar
data class LocadorRequest(
    val nome: String,
    val nomeImobiliaria: String? = null,
    val email: String,
    val telefone: String,
    val cnpj: String,
    val senha: String
)

