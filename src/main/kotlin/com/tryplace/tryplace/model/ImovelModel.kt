package com.tryplace.tryplace.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "imovel_db")
class ImovelModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @Column(nullable = false)
    var nomeImovel: String,
    @Column(nullable = false)
    var imagemImovel: String,
    @Column(nullable = false)
    var avaliacaoImovel: String,
    @Column(nullable = false)
    var descricaoImovel: String,
    @Column(nullable = false)
    var wifi: Boolean,
    @Column(nullable = false)
    var cafeDaManha: Boolean,
    @Column(nullable = false)
    var ruaImovel: String,
    @Column(nullable = false)
    var numeroImovel: String ,
    @Column(nullable = false)
    var bairroImovel: String,
    @Column(nullable = false)
    var cidadeImovel: String,
    @Column(nullable = false)
    var estadoImovel: String,
    @Column(nullable = false)
    var cepImovel: String


){

}