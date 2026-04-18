package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.LocadorModel
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(val email: String, val senha: String)

@RestController
@RequestMapping("/login") // ou "/api/login", dependendo de como você configurou seu Security
class AutenticacaoController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun efetuarLogin(@RequestBody dados: LoginRequest): ResponseEntity<TokenDto> {
        val tokenProvisorio = UsernamePasswordAuthenticationToken(dados.email, dados.senha)
        
        val autenticacao = manager.authenticate(tokenProvisorio)

        val tokenJWT = when (val usuarioAutenticado = autenticacao.principal) {
            is LocatarioModel -> tokenService.gerarToken(usuarioAutenticado)
            is LocadorModel -> tokenService.gerarToken(usuarioAutenticado)
            else -> throw RuntimeException("Tipo de usuário desconhecido")
        }

        return ResponseEntity.ok(TokenDto(tokenJWT))
    }
}