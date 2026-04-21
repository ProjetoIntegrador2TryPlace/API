package com.tryplace.tryplace.dto

import java.math.BigDecimal

data class ImovelRequest(
    val nomeImovel: String,
    val imagemImovel: String,
    val valorAluguel: BigDecimal,
    val avaliacaoImovel: String,
    val descricaoImovel: String,
    val quantidadeQuarto: Int,
    val quantidadeBanheiro: Int,
    val tipoImovel: String,
    val wifi: Boolean,
    val cafeDaManha: Boolean,
    val ruaImovel: String,
    val numeroImovel: String,
    val bairroImovel: String,
    val cidadeImovel: String,
    val estadoImovel: String,
    val cepImovel: String
)
