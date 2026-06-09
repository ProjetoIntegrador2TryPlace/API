package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.AvaliacaoAnuncianteRequest
import com.tryplace.tryplace.model.AvaliacaoAnuncianteModel
import com.tryplace.tryplace.service.AvaliacaoAnuncianteService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/avaliacoes")
class AvaliacaoAnuncianteController(
    private val avaliacaoAnuncianteService: AvaliacaoAnuncianteService
) {

    @PostMapping
    fun criarAvaliacao(@RequestBody request: AvaliacaoAnuncianteRequest): ResponseEntity<*> {
        return try {
            val novaAvaliacao = avaliacaoAnuncianteService.criarAvaliacao(
                avaliadorId = request.avaliadorId,
                anuncianteId = request.anuncianteId,
                nota = request.nota,
                comentarioRaw = request.comentario
            )
            ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao)

        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/anunciante/{anuncianteId}")
    fun listarAvaliacoes(
        @PathVariable anuncianteId: UUID,
        pageable: Pageable
    ): ResponseEntity<*> {
        val avaliacoes = avaliacaoAnuncianteService.listarAvaliacoes(anuncianteId, pageable)
        return ResponseEntity.ok(avaliacoes)
    }

    @GetMapping("/media/{anuncianteId}")
    fun calcularMedia(@PathVariable anuncianteId: UUID): ResponseEntity<Map<String, Any>> {
        val media = avaliacaoAnuncianteService.calcularMediaAvaliacoes(anuncianteId)
        val quantidade = avaliacaoAnuncianteService.contarAvaliacoes(anuncianteId)
        return ResponseEntity.ok(mapOf(
            "media" to media,
            "quantidade" to quantidade
        ))
    }

    @GetMapping("/verificar")
    fun verificarAvaliacao(
        @RequestParam avaliadorId: UUID,
        @RequestParam anuncianteId: UUID
    ): ResponseEntity<Map<String, Boolean>> {
        val jaAvaliou = avaliacaoAnuncianteService.jaAvaliou(avaliadorId, anuncianteId)
        return ResponseEntity.ok(mapOf("jaAvaliou" to jaAvaliou))
    }
}