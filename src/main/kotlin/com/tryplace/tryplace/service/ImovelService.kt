package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.ImovelDto
import com.tryplace.tryplace.dto.ImovelRequest
import com.tryplace.tryplace.exceptions.RecursoNaoEncontradoException
import com.tryplace.tryplace.model.ImovelModel
import com.tryplace.tryplace.repository.ImovelRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

        return mapearParaDto(savedImovel)

    }

    fun listarImovel(paginacao: Pageable): Page<ImovelDto> {
        val imoveisPage = repository.findAll(paginacao)

        return imoveisPage.map { model -> mapearParaDto(model) }
    }

    fun editarImovel(id: UUID, request: ImovelRequest): ImovelDto {
        val imovelExistente = repository.findById(id).orElseThrow {
            RecursoNaoEncontradoException("Imóvel com ID $id não encontrado para edição")
        }

        imovelExistente.nomeImovel = request.nomeImovel.trim()
        imovelExistente.imagemImovel = request.imagemImovel
        imovelExistente.avaliacaoImovel = request.avaliacaoImovel
        imovelExistente.descricaoImovel = request.descricaoImovel
        imovelExistente.wifi = request.wifi
        imovelExistente.cafeDaManha = request.cafeDaManha
        imovelExistente.ruaImovel = request.ruaImovel.trim()
        imovelExistente.numeroImovel = request.numeroImovel.trim()
        imovelExistente.bairroImovel = request.bairroImovel.trim()
        imovelExistente.cidadeImovel = request.cidadeImovel.trim()
        imovelExistente.estadoImovel = request.estadoImovel.trim()
        imovelExistente.cepImovel = request.cepImovel.trim()

        val imovelAtualizado = repository.save(imovelExistente)

        return mapearParaDto(imovelAtualizado)
    }

    fun deleteImovel(id: UUID) {
        val imovelExistente = repository.findById(id).orElseThrow {
            RecursoNaoEncontradoException("Imóvel com ID $id não encontrado para deletar")
        }
        repository.delete(imovelExistente)
    }

    private fun mapearParaDto(imovel: ImovelModel): ImovelDto {
        return ImovelDto(
            id = imovel.id!!,
            nomeImovel = imovel.nomeImovel,
            imagemImovel = imovel.imagemImovel,
            avaliacaoImovel = imovel.avaliacaoImovel,
            descricaoImovel = imovel.descricaoImovel,
            wifi = imovel.wifi,
            cafeDaManha = imovel.cafeDaManha,
            ruaImovel = imovel.ruaImovel,
            numeroImovel = imovel.numeroImovel,
            bairroImovel = imovel.bairroImovel,
            cidadeImovel = imovel.cidadeImovel,
            estadoImovel = imovel.estadoImovel,
            cepImovel = imovel.cepImovel
        )
    }


}