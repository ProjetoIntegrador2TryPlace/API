package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.UsuarioModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UsuarioRepository: JpaRepository<UsuarioModel, UUID> {
    fun findByEmail(email: String): UsuarioModel?

    fun existsByEmail(email: String): Boolean
    fun existsByCpfCnpj(cpfCnpj: String): Boolean

    @Query("""
        SELECT u FROM UsuarioModel u 
        WHERE u.interesseDividir = true 
        AND u.id != :requesterId 
        AND (:curso IS NULL OR u.cursoPeriodo = :curso)
        AND (:genero IS NULL OR u.genero = :genero)
    """)
    fun buscarPerfisCoabitacao(
        @Param("requesterId") requesterId: UUID,
        @Param("curso") curso: String?,
        @Param("genero") genero: String?,
        paginacao: Pageable
    ): Page<UsuarioModel>
}