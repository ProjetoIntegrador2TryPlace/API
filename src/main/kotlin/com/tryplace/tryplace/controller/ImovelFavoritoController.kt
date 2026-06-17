package com.tryplace.tryplace.controller

import com.tryplace.tryplace.model.ImovelFavoritoModel
import com.tryplace.tryplace.service.ImovelFavoritoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/favoritos")
class ImovelFavoritoController(
    private val favoritoService: ImovelFavoritoService
) {

    @PostMapping("/{imovelId}/usuario/{usuarioId}")
    fun favoritar(
        @PathVariable imovelId: UUID,
        @PathVariable usuarioId: UUID
    ): ResponseEntity<ImovelFavoritoModel> {
        val favorito = favoritoService.favoritar(usuarioId, imovelId)
        return ResponseEntity.status(HttpStatus.CREATED).body(favorito)
    }

    @DeleteMapping("/{imovelId}/usuario/{usuarioId}")
    fun desfavoritar(
        @PathVariable imovelId: UUID,
        @PathVariable usuarioId: UUID
    ): ResponseEntity<Void> {
        favoritoService.desfavoritar(usuarioId, imovelId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/usuario/{usuarioId}")
    fun listarFavoritos(
        @PathVariable usuarioId: UUID
    ): ResponseEntity<List<ImovelFavoritoModel>> {
        val favoritos = favoritoService.listarFavoritosDoUsuario(usuarioId)
        return ResponseEntity.ok(favoritos)
    }

    @GetMapping("/{imovelId}/usuario/{usuarioId}/status")
    fun checarStatusFavorito(
        @PathVariable imovelId: UUID,
        @PathVariable usuarioId: UUID
    ): ResponseEntity<Map<String, Boolean>> {
        val isFavorito = favoritoService.verificarSeEstaFavoritado(usuarioId, imovelId)
        return ResponseEntity.ok(mapOf("favoritado" to isFavorito))
    }
}