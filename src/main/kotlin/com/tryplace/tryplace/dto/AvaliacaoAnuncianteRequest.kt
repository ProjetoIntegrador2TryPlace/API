package com.tryplace.tryplace.dto

data class AvaliacaoAnuncianteRequest(
    val avaliadorId: Long,
    val anuncianteId: Long,
    val nota: Int,
    val comentario: String?
)
