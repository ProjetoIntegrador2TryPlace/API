package com.tryplace.tryplace.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "favoritos_imoveis")
data class ImovelFavoritoModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val avaliadorId: UUID,
    @Column(nullable = false)
    val imovelId: UUID,
    @Column(nullable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now()
)
