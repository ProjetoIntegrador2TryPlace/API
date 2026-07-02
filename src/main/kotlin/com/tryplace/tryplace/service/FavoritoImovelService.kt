package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.ImovelFavoritoModel
import com.tryplace.tryplace.repository.FavoritoImovelRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ImovelFavoritoService(
    private val favoritoRepository: FavoritoImovelRepository
) {

    fun favoritar(avaliadorId: UUID, imovelId: UUID): ImovelFavoritoModel {
        if (favoritoRepository.existsByAvaliadorIdAndImovelId(avaliadorId, imovelId)) {
            throw RuntimeException("Este imóvel já está nos seus favoritos.")
        }

        val novoFavorito = ImovelFavoritoModel(
            avaliadorId = avaliadorId,
            imovelId = imovelId
        )
        return favoritoRepository.save(novoFavorito)
    }

    fun desfavoritar(avaliadorId: UUID, imovelId: UUID) {
        val favorito = favoritoRepository.findByAvaliadorIdAndImovelId(avaliadorId, imovelId)
            ?: throw RuntimeException("Favorito não encontrado.")

        favoritoRepository.delete(favorito)
    }

    fun listarFavoritosDoUsuario(avaliadorId: UUID): List<ImovelFavoritoModel> {
        return favoritoRepository.findByAvaliadorId(avaliadorId)
    }

    fun verificarSeEstaFavoritado(avaliadorId: UUID, imovelId: UUID): Boolean {
        return favoritoRepository.existsByAvaliadorIdAndImovelId(avaliadorId, imovelId)
    }
}