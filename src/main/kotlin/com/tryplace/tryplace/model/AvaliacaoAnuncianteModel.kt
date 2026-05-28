package com.tryplace.tryplace.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "avaliacoes_anunciantes")
data class AvaliacaoAnuncianteModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val avaliadorId: Long,

    @Column(nullable = false)
    val anuncianteId: Long,

    @Column(nullable = false)
    val nota: Int,

    @Column(length = 500)
    var comentario: String? = null,

    @Column(nullable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now()
)