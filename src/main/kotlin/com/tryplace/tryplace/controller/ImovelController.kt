package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.AtualizarPerfilRequest
import com.tryplace.tryplace.dto.ImovelDto
import com.tryplace.tryplace.dto.ImovelRequest
import com.tryplace.tryplace.dto.ImovelVisitanteDto
import com.tryplace.tryplace.dto.UsuarioDto
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.repository.ImovelRepository
import com.tryplace.tryplace.service.ImovelService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

import java.util.UUID

@RestController
@RequestMapping("/api/imovel")
class ImovelController(
    private val service: ImovelService
) {


    @GetMapping("/visitante")
    fun listarParaVisitante(): List<ImovelVisitanteDto> {
        return service.listarImovelTodos()
    }
    @GetMapping("/visitante/filtroPrecoMax/{precoMax}")
    fun buscarPorPrecoMax(@PathVariable precoMax: BigDecimal): List<ImovelVisitanteDto> {
        return service.buscaPorPrecoMaxVisitante(precoMax)
    }

    @GetMapping("/visitante/tipoImovel/{tipoImovel}")
    fun buscarPorTipoImovel(@PathVariable tipoImovel: String): List<ImovelVisitanteDto> {
        return service.buscaPorTipoImovelVisitante(tipoImovel)
    }

    @GetMapping("/visitante/filtroPrecoMinMax")
    fun buscarPorPrecoMinMax(
        @RequestParam precoMin: BigDecimal,
        @RequestParam precoMax: BigDecimal
    ) : ResponseEntity<List<ImovelDto>> {
        val imovel = service.buscarPorPrecoMinMax(precoMin, precoMax)
        return ResponseEntity.ok(imovel)
    }


    @PostMapping
    fun criarImovel(
        @RequestBody request: ImovelRequest,
        @AuthenticationPrincipal donoLogado: UsuarioModel
    ): ImovelDto {
        return service.criarImovel(request, donoLogado)
    }

    @GetMapping
    fun listarImovel(
        @PageableDefault(size = 10, page = 0) paginacao: Pageable
    ): Page<ImovelDto> {
        return service.listarImovel(paginacao)
    }

    @PutMapping("/{id}")
    fun editarImovel(@PathVariable id: UUID, @RequestBody request: ImovelRequest,@AuthenticationPrincipal donoLogado: UsuarioModel): ImovelDto {
        return service.editarImovel(id, request, donoLogado)
    }

    @DeleteMapping("/{id}")
    fun deletarImovel (@PathVariable id: UUID,@AuthenticationPrincipal donoLogado: UsuarioModel) {
        service.deleteImovel(id, donoLogado)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ImovelDto {
        return service.buscarImovelCadastradoPorId(id)
    }
    @GetMapping("/meusImoveis")
    fun listarMeusImoveis(@AuthenticationPrincipal donoLogado: UsuarioModel): ResponseEntity<List<ImovelDto>> {
        val meusImoveis = service.listarMeusImoveis(donoLogado)
        return ResponseEntity.ok(meusImoveis)
    }
    @GetMapping("/nome")
    fun buscarImovelPorNome(@RequestParam(name = "busca") nomeImovel: String): ResponseEntity<List<ImovelDto>> {
        val imoveisPorNome = service.buscarImovelPorNome(nomeImovel)
        if (imoveisPorNome.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.ok(imoveisPorNome)

    }
}