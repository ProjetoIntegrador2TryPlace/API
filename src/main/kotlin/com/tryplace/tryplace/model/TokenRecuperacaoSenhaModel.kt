package com.tryplace.tryplace.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "token_recuperacao_tb")
class TokenRecuperacaoSenhaModel (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var codigoToken: String,


    @Column(nullable = false)
    var dataExpiracao: LocalDateTime,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    var usuario: UsuarioModel

) {
    fun Expirado(): Boolean {
        return LocalDateTime.now().isAfter(dataExpiracao)
    }
}