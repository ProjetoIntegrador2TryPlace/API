package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.UsuarioModel
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

        val usuario = autenticacao.principal as UsuarioModel

        // E então geramos o token diretamente para esse usuário
        val tokenJWT = tokenService.gerarToken(usuario)

        return ResponseEntity.ok(TokenDto(tokenJWT))
    }
}