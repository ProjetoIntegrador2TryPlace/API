package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.ImovelModel
import com.tryplace.tryplace.model.UsuarioModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.UUID

@Repository
interface ImovelRepository: JpaRepository<ImovelModel, UUID>{

    fun findByValorAluguelLessThanEqual(precoMax: BigDecimal): List<ImovelModel>

    fun findByValorAluguelBetween(precoMin: BigDecimal, PrecoMax: BigDecimal): List<ImovelModel>

    fun findByTipoImovel(tipoImovel: String): List<ImovelModel>

    fun findAllByDono(dono: UsuarioModel): List<ImovelModel>
}