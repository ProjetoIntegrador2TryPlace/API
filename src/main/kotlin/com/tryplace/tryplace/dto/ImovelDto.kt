package com.tryplace.tryplace.dto

import java.math.BigDecimal
import java.util.UUID

data class ImovelDto(
    val id: UUID,
    val nomeImovel: String,
    val imagemImovel: String,
    val valorAluguel: BigDecimal,
    val avaliacaoImovel: String,
    val descricaoImovel: String,
    val quantidadeBanheiro: Int,
    val quantidadeQuarto: Int,
    val tipoImovel: String,
    val telefoneLocador: String,
    val tipoAnunciante: String,
    val wifi: Boolean,
    val cafeDaManha: Boolean,
    val ruaImovel: String,
    val numeroImovel: String,
    val bairroImovel: String,
    val cidadeImovel: String,
    val estadoImovel: String,
    val cepImovel: String,
    val latitude: Double?,
    val longitude: Double?,
    val localizacaoExata: Boolean


)
