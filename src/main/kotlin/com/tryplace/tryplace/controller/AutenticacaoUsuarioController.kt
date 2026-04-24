package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.LocadorModel
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

data class LoginRequest(val email: String, val senha: String)

@RestController
@RequestMapping("/api/login")
class AutenticacaoController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun efetuarLogin(@RequestBody dados: LoginRequest): ResponseEntity<TokenDto> {

        val tokenProvisorio =
            UsernamePasswordAuthenticationToken(dados.email, dados.senha)

        val autenticacao = manager.authenticate(tokenProvisorio)

        val usuario = autenticacao.principal

        val tokenJWT = when (usuario) {
            is LocatarioModel -> tokenService.gerarToken(usuario)
            is LocadorModel -> tokenService.gerarToken(usuario)
            else -> throw RuntimeException("Tipo de usuário desconhecido")
        }

        return ResponseEntity.ok(TokenDto(tokenJWT))
    }
}