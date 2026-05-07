package com.tryplace.tryplace.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "token_recuperacao_tb")
class TokenRecuperacaoSenhaModel(
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
    fun isExpirado() = LocalDateTime.now().isAfter(dataExpiracao)
}