package com.tryplace.tryplace.dto

import java.util.UUID

data class AvaliacaoAnuncianteRequest(
    val avaliadorId: UUID,
    val anuncianteId: UUID,
    val nota: Int,
    val comentario: String?
)