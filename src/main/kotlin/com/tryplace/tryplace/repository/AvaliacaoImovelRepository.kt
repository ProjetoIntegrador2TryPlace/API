package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.AvaliacaoImovelModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AvaliacaoImovelRepository : JpaRepository<AvaliacaoImovelModel, Long>{
    fun existsByAvaliadorIdAndImovelId(avaliadorId: UUID, imovelId: UUID) : Boolean

    fun findByImovelIdOrderByDataCriacaoDesc(imovelId: UUID, pageable: Pageable): Page<AvaliacaoImovelModel>

    fun findByImovelId(imovelId: UUID): List<AvaliacaoImovelModel>
}