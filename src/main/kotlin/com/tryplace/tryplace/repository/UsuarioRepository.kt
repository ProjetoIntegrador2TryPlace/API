package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.UsuarioModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UsuarioRepository: JpaRepository<UsuarioModel, UUID> {
    fun findByEmail(email: String): UsuarioModel?

    fun existsByEmail(email: String): Boolean
    fun existsByCpfCnpj(cpfCnpj: String): Boolean
}