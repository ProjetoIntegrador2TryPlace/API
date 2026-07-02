package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.AvaliacaoImovelRequest
import com.tryplace.tryplace.service.AvaliacaoImovelService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/avaliacoesImovel")
class AvaliacaoImovelController(
    private val avaliacaoImovelService: AvaliacaoImovelService
) {
    @PostMapping
    fun criarAvaliacao(@RequestBody request: AvaliacaoImovelRequest): ResponseEntity<*> {
        return try {
            val novaAvaliacao = avaliacaoImovelService.criarAvaliacaoImovel(
                avaliadorId = request.avaliadorId,
                imovelId = request.imovelId,
                nota = request.nota,
                comentarioRaw = request.comentario
            )
            ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao)
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/imovel/{imovelId}")
    fun listarAvaliacoes(
        @PathVariable imovelId: UUID,
        pageable: Pageable
    ): ResponseEntity<*> {
        val avaliacoes = avaliacaoImovelService.listarAvaliacoesImovel(imovelId, pageable)
        return ResponseEntity.ok(avaliacoes)
    }

    @GetMapping("/media/{imovelId}")
    fun calcularMedia(@PathVariable imovelId: UUID): ResponseEntity<Map<String, Any>> {
        val media = avaliacaoImovelService.calcularMediaAvaliacoes(imovelId)
        val quantidade = avaliacaoImovelService.contarAvaliacoes(imovelId)
        return ResponseEntity.ok(mapOf(
            "media" to media,
            "quantidade" to quantidade
        ))
    }

    @GetMapping("/verificar")
    fun verificarAvaliacao(
        @RequestParam avaliadorId: UUID,
        @RequestParam imovelId: UUID
    ): ResponseEntity<Map<String, Boolean>> {
        val jaAvaliou = avaliacaoImovelService.jaAvaliou(avaliadorId, imovelId)
        return ResponseEntity.ok(mapOf("jaAvaliou" to jaAvaliou))
    }
}