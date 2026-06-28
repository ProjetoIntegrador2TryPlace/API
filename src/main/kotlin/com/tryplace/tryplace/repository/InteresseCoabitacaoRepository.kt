package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.InteresseCoabitacaoModel
import com.tryplace.tryplace.model.UsuarioModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface InteresseCoabitacaoRepository : JpaRepository<InteresseCoabitacaoModel, UUID> {
    fun findAllByUsuario(usuario: UsuarioModel): List<InteresseCoabitacaoModel>
}