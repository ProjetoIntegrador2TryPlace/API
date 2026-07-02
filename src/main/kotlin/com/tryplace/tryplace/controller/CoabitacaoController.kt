package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.PerfilCoabitacaoDetalheDto
import com.tryplace.tryplace.dto.PerfilCoabitacaoResumoDto
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.service.CoabitacaoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/coabitacao")
class CoabitacaoController(
    private val service: CoabitacaoService
) {

    @GetMapping("/feed")
    fun listarFeedCoabitacao(
        @AuthenticationPrincipal usuarioLogado: UsuarioModel,
        @RequestParam(required = false) curso: String?,
        @RequestParam(required = false) genero: String?,
        @RequestParam(required = false) badge: String?,
        @PageableDefault(size = 20, page = 0) paginacao: Pageable
    ): ResponseEntity<Page<PerfilCoabitacaoResumoDto>> {
        val feed = service.listarPerfis(usuarioLogado, curso, genero, badge, paginacao)
        return ResponseEntity.ok(feed)
    }

    @GetMapping("/perfil/{id}")
    fun buscarDetalhesModal(
        @PathVariable id: UUID,
        @AuthenticationPrincipal usuarioLogado: UsuarioModel
    ): ResponseEntity<PerfilCoabitacaoDetalheDto> {
        val detalhes = service.buscarDetalhesPerfil(id, usuarioLogado)
        return ResponseEntity.ok(detalhes)
    }

    @PatchMapping("/status")
    fun atualizarMeuStatus(
        @AuthenticationPrincipal usuarioLogado: UsuarioModel,
        @RequestParam ativar: Boolean
    ): ResponseEntity<Void> {
        service.alterarStatusCoabitacao(usuarioLogado, ativar)
        return ResponseEntity.ok().build()
    }
}