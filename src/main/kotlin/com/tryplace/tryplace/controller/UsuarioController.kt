package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.AtualizarPerfilRequest
import com.tryplace.tryplace.dto.UsuarioDto
import com.tryplace.tryplace.dto.UsuarioRequest
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.service.UsuarioService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuario")
class UsuarioController(
    private val usuarioservice: UsuarioService
) {

    @PostMapping("/registrarUsuario")
    fun cadastrarUsuario(@RequestBody @Valid request: UsuarioRequest, httpRequest: HttpServletRequest): ResponseEntity<UsuarioDto> {
        val ipDaConexao = httpRequest.remoteAddr
        val usuarioSalvo = usuarioservice.cadastrarUsuario(request, ipDaConexao)
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo)
    }

    @PutMapping("/meuPerfil")
    fun atualizarMeuPerfil(
        @RequestBody request: AtualizarPerfilRequest,
        @AuthenticationPrincipal usuarioLogado: UsuarioModel
    ): ResponseEntity<UsuarioDto> {
        val perfilAtualizado = usuarioservice.atualizarPerfil(usuarioLogado, request)
        return ResponseEntity.ok(perfilAtualizado)
    }
}