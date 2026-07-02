package com.tryplace.tryplace.dto

import java.util.UUID

data class AvaliacaoImovelRequest(
    val avaliadorId: UUID,
    val imovelId: UUID,
    val nota: Int,
    val comentario: String?
)