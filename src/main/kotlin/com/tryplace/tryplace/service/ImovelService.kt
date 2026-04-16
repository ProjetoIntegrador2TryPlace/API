package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.ImovelDto
import com.tryplace.tryplace.dto.ImovelRequest
import com.tryplace.tryplace.exceptions.RecursoNaoEncontradoException
import com.tryplace.tryplace.model.ImovelModel
import com.tryplace.tryplace.repository.ImovelRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ImovelService(
    private val repository: ImovelRepository
) {
    fun criarImovel (request: ImovelRequest): ImovelDto {
        val imovel = ImovelModel (
            nomeImovel = request.nomeImovel.trim(),
            imagemImovel = request.imagemImovel,
            avaliacaoImovel = request.avaliacaoImovel,
            descricaoImovel = request.descricaoImovel,
            wifi = request.wifi,
            cafeDaManha = request.cafeDaManha,
            ruaImovel = request.ruaImovel.trim(),
            numeroImovel = request.numeroImovel.trim(),
            bairroImovel = request.bairroImovel.trim(),
            cidadeImovel = request.cidadeImovel.trim(),
            estadoImovel = request.estadoImovel.trim(),
            cepImovel = request.cepImovel.trim()
        )

        val savedImovel = repository.save(imovel)

        return ImovelDto (
            id = savedImovel.id!!,
            nomeImovel = savedImovel.nomeImovel,
            imagemImovel = savedImovel.imagemImovel,
            avaliacaoImovel = savedImovel.avaliacaoImovel,
            descricaoImovel = savedImovel.descricaoImovel,
            wifi = savedImovel.wifi,
            cafeDaManha = savedImovel.cafeDaManha,
            ruaImovel = savedImovel.ruaImovel,
            numeroImovel = savedImovel.numeroImovel,
            bairroImovel = savedImovel.bairroImovel,
            cidadeImovel = savedImovel.cidadeImovel,
            estadoImovel = savedImovel.estadoImovel,
            cepImovel = savedImovel.cepImovel

        )

    }

//    fun listarImovel() {
//        repository.findByNome()
//    }
//
//    fun editarImovel() {
//        repository.save()
//    }

    fun deleteImovel(id: UUID) {
        val imovelExistente = repository.findById(id).orElseThrow {
            RecursoNaoEncontradoException("Imóvel com ID $id não encontrado para deletar")
        }
        repository.delete(imovelExistente)
    }
}