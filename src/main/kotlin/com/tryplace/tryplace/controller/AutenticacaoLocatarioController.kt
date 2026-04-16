package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LoginLocatarioRequest
import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.service.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/login")
class AutenticacaoLocatarioController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) {
    @PostMapping
    fun efetuarLogin(@RequestBody dados: LoginLocatarioRequest): TokenDto {
        val tokenProvisorio = UsernamePasswordAuthenticationToken(dados.email, dados.senha)

        val autenticacao = manager.authenticate(tokenProvisorio)

        val tokenJWT = tokenService.gerarToken(autenticacao.principal as LocatarioModel)

        return TokenDto(tokenJWT)
    }
}