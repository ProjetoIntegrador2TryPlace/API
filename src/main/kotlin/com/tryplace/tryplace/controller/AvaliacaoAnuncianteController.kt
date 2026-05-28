package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.AvaliacaoAnuncianteRequest
import com.tryplace.tryplace.model.AvaliacaoAnuncianteModel
import com.tryplace.tryplace.service.AvaliacaoAnuncianteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
}