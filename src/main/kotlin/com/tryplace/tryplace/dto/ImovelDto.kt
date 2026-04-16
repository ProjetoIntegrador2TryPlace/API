package com.tryplace.tryplace.dto

import java.util.UUID

data class ImovelDto(
    val id: UUID,
    val nomeImovel: String,
    val imagemImovel: String,
    val avaliacaoImovel: String,
    val descricaoImovel: String,
    val wifi: Boolean,
    val cafeDaManha: Boolean,
    val ruaImovel: String,
    val numeroImovel: String,
    val bairroImovel: String,
    val cidadeImovel: String,
    val estadoImovel: String,
    val cepImovel: String

)
