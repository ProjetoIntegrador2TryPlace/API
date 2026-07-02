package com.tryplace.tryplace.dto

import java.math.BigDecimal
import java.util.UUID

data class ImovelVisitanteDto(
    val id: UUID,
    val imagemImovel: String,
    val valorAluguel: BigDecimal,
    val bairroImovel: String,
    val quantidadeQuarto: Int,
    val quantidadeBanheiro: Int,
    val tipoImovel: String,
    val status: String
)
