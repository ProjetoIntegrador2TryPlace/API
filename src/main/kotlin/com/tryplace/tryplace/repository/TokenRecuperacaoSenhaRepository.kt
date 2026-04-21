package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.TokenRecuperacaoSenhaModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface TokenRecuperacaoSenhaRepository: JpaRepository<TokenRecuperacaoSenhaModel, UUID> {
    fun findByCodigoToken(codigoToken: String): Optional<TokenRecuperacaoSenhaModel>
}