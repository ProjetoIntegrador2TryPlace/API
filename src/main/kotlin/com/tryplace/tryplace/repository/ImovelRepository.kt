package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.ImovelModel
import com.tryplace.tryplace.model.UsuarioModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.UUID

@Repository
interface ImovelRepository : JpaRepository<ImovelModel, UUID> {

    fun findByValorAluguelLessThanEqual(precoMax: BigDecimal, pageable: Pageable): Page<ImovelModel>

    fun findByValorAluguelBetween(precoMin: BigDecimal, precoMax: BigDecimal, pageable: Pageable): Page<ImovelModel>

    fun findByTipoImovel(tipoImovel: String, pageable: Pageable): Page<ImovelModel>

    fun findAllByDono(dono: UsuarioModel): List<ImovelModel>

    fun findByNomeImovelContainingIgnoreCase(nomeImovel: String, pageable: Pageable): Page<ImovelModel>
}