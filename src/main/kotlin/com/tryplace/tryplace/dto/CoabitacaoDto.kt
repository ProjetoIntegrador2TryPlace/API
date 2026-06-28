package com.tryplace.tryplace.dto

import java.util.UUID

data class PerfilCoabitacaoResumoDto(
    val id: UUID,
    val nome: String,
    val idade: Int,
    val cursoPeriodo: String?,
    val badges: List<String>
)

data class PerfilCoabitacaoDetalheDto(
    val id: UUID,
    val nome: String,
    val biografia: String?,
    val badges: List<String>,
    val imoveisInteresse: List<ImovelDto>
)