package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.AvaliacaoAnuncianteModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AvaliacaoAnuncianteRepository : JpaRepository<AvaliacaoAnuncianteModel, Long> {

    fun existsByAvaliadorIdAndAnuncianteId(avaliadorId: Long, anuncianteId: Long): Boolean

    fun findByAnuncianteIdOrderByDataCriacaoDesc(anuncianteId: Long, pageable: Pageable): Page<AvaliacaoAnuncianteModel>
}