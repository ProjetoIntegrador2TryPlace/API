package com.tryplace.tryplace.repository

import com.tryplace.tryplace.dto.LocadorDto
import com.tryplace.tryplace.model.LocadorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID



@Repository
interface LocadorRepository: JpaRepository<LocadorModel, UUID> {
    fun findByNome(nome:String): LocadorDto
}