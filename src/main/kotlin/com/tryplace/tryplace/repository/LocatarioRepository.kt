package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.LocatarioModel
import jakarta.validation.constraints.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocatarioRepository: JpaRepository<LocatarioModel, UUID>{
    fun findByNome(nome: String): LocatarioModel?

    fun findByEmail(email: String): LocatarioModel?
}