package com.tryplace.tryplace.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "interesse_coabitacao_tb")
class InteresseCoabitacaoModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    var usuario: UsuarioModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imovel_id", nullable = false)
    var imovel: ImovelModel
)