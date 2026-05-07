package com.tryplace.tryplace.dto

data class AtualizarPerfilRequest(
    val cpfCnpj: String? = null,
    val nomeEmpresa: String? = null,
    val cursoPeriodo: String? = null,
    val interesseDividir: Boolean? = null,

    val descricaoHabito: String? = null,
    val genero: String? = null,
    val termoResponsabilidade: Boolean? = null,
    val badges: List<String>? = null
)

