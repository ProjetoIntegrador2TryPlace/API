package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.AvaliacaoAnuncianteModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AvaliacaoAnuncianteRepository : JpaRepository<AvaliacaoAnuncianteModel, Long> {

    fun existsByAvaliadorIdAndAnuncianteId(avaliadorId: UUID, anuncianteId: UUID): Boolean

    fun findByAnuncianteIdOrderByDataCriacaoDesc(anuncianteId: UUID, pageable: Pageable): Page<AvaliacaoAnuncianteModel>

    fun findByAnuncianteId(anuncianteId: UUID): List<AvaliacaoAnuncianteModel>
}