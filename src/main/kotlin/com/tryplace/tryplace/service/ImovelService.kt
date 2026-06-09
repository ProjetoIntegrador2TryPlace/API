package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.ImovelDto
import com.tryplace.tryplace.dto.ImovelRequest
import com.tryplace.tryplace.dto.ImovelVisitanteDto
import com.tryplace.tryplace.exceptions.RecursoNaoEncontradoException
import com.tryplace.tryplace.exceptions.RegraDeNegocioException
import com.tryplace.tryplace.model.ImovelModel
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.repository.ImovelRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID
import kotlin.String

@Service
class ImovelService(
    private val repository: ImovelRepository,
    private val geocodingService: GeocodingService
) {

    fun buscarPorPrecoMinMax(precoMin: BigDecimal, precoMax: BigDecimal): List<ImovelDto> {
        if (precoMin > precoMax) {
            throw RegraDeNegocioException("O valor mínimo não pode ser maior que o valor maximo")
        }

        return repository.findByValorAluguelBetween(precoMin, precoMax).map { converterParaCadastradoDto(it) }
    }

    fun listarImovelTodos(): List<ImovelVisitanteDto> {
        return repository.findAll().map { converterParaVisitanteDto(it) }
    }

    fun buscaPorPrecoMaxVisitante(precoMax: BigDecimal): List<ImovelVisitanteDto> {
        return repository.findByValorAluguelLessThanEqual(precoMax).map { converterParaVisitanteDto(it) }
    }

    fun buscaPorTipoImovelVisitante(tipoImovel: String): List<ImovelVisitanteDto> {
        return repository.findByTipoImovel(tipoImovel).map { converterParaVisitanteDto(it) }
    }

    fun buscarImovelCadastradoPorId(id: UUID): ImovelDto {
        val imovel = repository.findById(id)
            .orElseThrow{ RecursoNaoEncontradoException("Imovel com $id não encontrado") }

        return converterParaCadastradoDto(imovel)
    }

    fun buscarImovelPorNome(nomeImovel: String): List<ImovelDto> {
        val imovel = repository.findByNomeImovelContainingIgnoreCase(nomeImovel)

        return imovel.map { imovel -> converterParaCadastradoDto(imovel)}
    }


    private fun converterParaVisitanteDto(im: ImovelModel) = ImovelVisitanteDto(
        id = im.id!!,
        imagemImovel = im.imagemImovel,
        valorAluguel = im.valorAluguel,
        bairroImovel = im.bairroImovel,
        quantidadeQuarto = im.quantidadeQuarto,
        quantidadeBanheiro = im.quantidadeBanheiro,
        tipoImovel = im.tipoImovel
    )

    private fun converterParaCadastradoDto(im: ImovelModel): ImovelDto {
        val docDonoLimpo = im.dono.cpfCnpj.filter { it.isDigit() }
        val tipoDono = if (docDonoLimpo.length == 14) "EMPRESA" else "PESSOA FISICA"

        return ImovelDto(
            id = im.id!!,
            nomeImovel = im.nomeImovel,
            imagemImovel = im.imagemImovel,
            valorAluguel = im.valorAluguel,
            avaliacaoImovel = im.avaliacaoImovel,
            descricaoImovel = im.descricaoImovel,
            quantidadeBanheiro = im.quantidadeBanheiro,
            quantidadeQuarto = im.quantidadeQuarto,
            tipoImovel = im.tipoImovel,
            telefoneLocador = im.dono.telefone,
            tipoAnunciante = tipoDono,
            wifi = im.wifi,
            cafeDaManha = im.cafeDaManha,
            ruaImovel = im.ruaImovel,
            numeroImovel = im.numeroImovel,
            bairroImovel = im.bairroImovel,
            cidadeImovel = im.cidadeImovel,
            estadoImovel = im.estadoImovel,
            cepImovel = im.cepImovel,
            latitude = im.latitude,
            longitude = im.longitude,
            localizacaoExata = im.localizacaoExata,
            donoId = im.dono.id!!  // ✅ ID do anunciante para avaliação
        )
    }

    fun criarImovel (request: ImovelRequest, donoLogado: UsuarioModel): ImovelDto {
        val imovel = ImovelModel (
            nomeImovel = request.nomeImovel.trim(),
            imagemImovel = request.imagemImovel,
            valorAluguel = request.valorAluguel,
            avaliacaoImovel = request.avaliacaoImovel,
            descricaoImovel = request.descricaoImovel,
            quantidadeQuarto = request.quantidadeQuarto,
            quantidadeBanheiro = request.quantidadeBanheiro,
            tipoImovel = request.tipoImovel,
            wifi = request.wifi,
            cafeDaManha = request.cafeDaManha,
            ruaImovel = request.ruaImovel.trim(),
            numeroImovel = request.numeroImovel.trim(),
            bairroImovel = request.bairroImovel.trim(),
            cidadeImovel = request.cidadeImovel.trim(),
            estadoImovel = request.estadoImovel.trim(),
            cepImovel = request.cepImovel.trim(),
            dono = donoLogado,
            localizacaoExata = request.localizacaoExata
        )

        val enderecoCompleto = "${imovel.ruaImovel}, ${imovel.numeroImovel}, ${imovel.bairroImovel}, ${imovel.cidadeImovel} - ${imovel.estadoImovel}"
        val coordenadas = geocodingService.buscarCoordenadas(enderecoCompleto)

        if (coordenadas != null) {
            imovel.latitude = coordenadas.first
            imovel.longitude = coordenadas.second
        }

        val savedImovel = repository.save(imovel)

        return converterParaCadastradoDto(savedImovel)
    }

    fun listarImovel(paginacao: Pageable): Page<ImovelDto> {
        val imoveisPage = repository.findAll(paginacao)

        return imoveisPage.map { model -> converterParaCadastradoDto(model) }
    }

    fun editarImovel(id: UUID, request: ImovelRequest, donoLogado: UsuarioModel): ImovelDto {
        val imovelExistente = repository.findById(id).orElseThrow {
            RecursoNaoEncontradoException("Imóvel com ID $id não encontrado para edição")
        }
        if (imovelExistente.dono.id != donoLogado.id) {
            throw RegraDeNegocioException("Você não tem permissão para editar esse imóvel")
        }

        imovelExistente.nomeImovel = request.nomeImovel.trim()
        imovelExistente.imagemImovel = request.imagemImovel
        imovelExistente.valorAluguel = request.valorAluguel
        imovelExistente.avaliacaoImovel = request.avaliacaoImovel
        imovelExistente.descricaoImovel = request.descricaoImovel
        imovelExistente.quantidadeQuarto = request.quantidadeQuarto
        imovelExistente.quantidadeBanheiro = request.quantidadeBanheiro
        imovelExistente.tipoImovel = request.tipoImovel
        imovelExistente.wifi = request.wifi
        imovelExistente.cafeDaManha = request.cafeDaManha
        imovelExistente.ruaImovel = request.ruaImovel.trim()
        imovelExistente.numeroImovel = request.numeroImovel.trim()
        imovelExistente.bairroImovel = request.bairroImovel.trim()
        imovelExistente.cidadeImovel = request.cidadeImovel.trim()
        imovelExistente.estadoImovel = request.estadoImovel.trim()
        imovelExistente.cepImovel = request.cepImovel.trim()

        val enderecoCompleto = "${imovelExistente.ruaImovel}, ${imovelExistente.numeroImovel}, ${imovelExistente.bairroImovel}, ${imovelExistente.cidadeImovel} - ${imovelExistente.estadoImovel}"
        val coordenadas = geocodingService.buscarCoordenadas(enderecoCompleto)

        if (coordenadas != null) {
            imovelExistente.latitude = coordenadas.first
            imovelExistente.longitude = coordenadas.second
        } else {
            imovelExistente.latitude = null
            imovelExistente.longitude = null
        }

        val imovelAtualizado = repository.save(imovelExistente)

        return converterParaCadastradoDto(imovelAtualizado)
    }

    fun deleteImovel(id: UUID, donoLogado: UsuarioModel) {
        val imovelExistente = repository.findById(id).orElseThrow {
            RecursoNaoEncontradoException("Imóvel com ID $id não encontrado para deletar")
        }
        if (imovelExistente.dono.id != donoLogado.id) {
            throw RegraDeNegocioException("Você não tem permissão para deletar esse imóvel")
        }
        repository.delete(imovelExistente)
    }

    fun listarMeusImoveis(donoLogado: UsuarioModel): List<ImovelDto> {
        val imoveis = repository.findAllByDono(donoLogado)
        return imoveis.map { converterParaCadastradoDto(it) }
    }
}