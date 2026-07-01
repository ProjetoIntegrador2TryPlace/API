package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.ImovelDto
import com.tryplace.tryplace.dto.ImovelRequest
import com.tryplace.tryplace.dto.ImovelVisitanteDto
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.service.ImovelService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.UUID

@RestController
@RequestMapping("/api/imovel")
class ImovelController(
    private val service: ImovelService
) {

    @GetMapping("/visitante")
    fun listarParaVisitante(
        @PageableDefault(size = 10, page = 0) paginacao: Pageable
    ): Page<ImovelVisitanteDto> {
        return service.listarImovelTodos(paginacao)
    }

    @GetMapping("/visitante/filtroPrecoMax/{precoMax}")
    fun buscarPorPrecoMax(
        @PathVariable precoMax: BigDecimal,
        @PageableDefault(size = 10, page = 0) paginacao: Pageable
    ): Page<ImovelVisitanteDto> {
        return service.buscaPorPrecoMaxVisitante(precoMax, paginacao)
    }

    @GetMapping("/visitante/{id}")
    fun buscarDetalhesVisitante(@PathVariable id: UUID): ImovelVisitanteDto {
        return service.buscarImovelVisitantePorId(id)
    }

    @GetMapping("/visitante/tipoImovel/{tipoImovel}")
    fun buscarPorTipoImovel(
        @PathVariable tipoImovel: String,
        @PageableDefault(size = 10, page = 0) paginacao: Pageable
    ): Page<ImovelVisitanteDto> {
        return service.buscaPorTipoImovelVisitante(tipoImovel, paginacao)
    }

    @GetMapping("/visitante/filtroPrecoMinMax")
    fun buscarPorPrecoMinMax(
        @RequestParam precoMin: BigDecimal,
        @RequestParam precoMax: BigDecimal,
        @PageableDefault(size = 10, page = 0) paginacao: Pageable
    ): ResponseEntity<Page<ImovelDto>> {
        val imovel = service.buscarPorPrecoMinMax(precoMin, precoMax, paginacao)
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
    fun editarImovel(
        @PathVariable id: UUID,
        @RequestBody request: ImovelRequest,
        @AuthenticationPrincipal donoLogado: UsuarioModel
    ): ImovelDto {
        return service.editarImovel(id, request, donoLogado)
    }

    @DeleteMapping("/{id}")
    fun deletarImovel(
        @PathVariable id: UUID,
        @AuthenticationPrincipal donoLogado: UsuarioModel
    ) {
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

    @PatchMapping("/{id}/status")
    fun alterarStatus(
        @PathVariable id: UUID,
        @RequestParam status: String,
        @AuthenticationPrincipal donoLogado: UsuarioModel
    ): ResponseEntity<ImovelDto> {
        val imovelAtualizado = service.alterarStatusImovel(id, status, donoLogado)
        return ResponseEntity.ok(imovelAtualizado)
    }

    @GetMapping("/nome")
    fun buscarImovelPorNome(
        @RequestParam(name = "busca") nomeImovel: String,
        @PageableDefault(size = 10, page = 0) paginacao: Pageable
    ): ResponseEntity<Page<ImovelDto>> {
        val imoveisPorNome = service.buscarImovelPorNome(nomeImovel, paginacao)
        if (imoveisPorNome.isEmpty) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(imoveisPorNome)
    }
}