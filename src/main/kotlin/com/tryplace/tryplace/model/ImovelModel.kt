package com.tryplace.tryplace.model

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "imovel_tb")
class ImovelModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @Column(nullable = false)
    var nomeImovel: String,
    @Column(nullable = false)
    var imagemImovel: String,
    @Column(nullable = false)
    var valorAluguel: BigDecimal,
    @Column(nullable = false)
    var avaliacaoImovel: String,
    @Column(nullable = false)
    var descricaoImovel: String,
    @Column(nullable = false)
    var quantidadeQuarto: Int,
    @Column(nullable = false)
    var quantidadeBanheiro: Int,
    @Column(nullable = false)
    var tipoImovel: String,
    @Column(nullable = false)
    var wifi: Boolean = false,
    @Column(nullable = false)
    var cafeDaManha: Boolean = false,
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
    var cepImovel: String,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dono_id", nullable = false)
    var dono: UsuarioModel


){

}