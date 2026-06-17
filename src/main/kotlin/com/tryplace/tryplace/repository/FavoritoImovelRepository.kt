package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.ImovelFavoritoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FavoritoImovelRepository : JpaRepository<ImovelFavoritoModel, Long> {
    fun existsByAvaliadorIdAndImovelId(avaliadorId: UUID, imovelId: UUID) : Boolean
    fun findByAvaliadorId(avaliadorId: UUID): List<ImovelFavoritoModel>
    fun findByAvaliadorIdAndImovelId(avaliadorId: UUID, imovelId: UUID): ImovelFavoritoModel?
}