package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LoginUsuarioRequest
import com.tryplace.tryplace.dto.TokenDto
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.service.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/login")
class AutenticacaoUsuarioController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) {
    @PostMapping
    fun efetuarLogin(@RequestBody dados: LoginUsuarioRequest): TokenDto {
        val tokenProvisorio = UsernamePasswordAuthenticationToken(dados.email, dados.senha)

        val autenticacao = manager.authenticate((tokenProvisorio))

        val tokenJWT = tokenService.gerarToken(usuario = autenticacao.principal as UsuarioModel)


        return TokenDto(tokenJWT)
    }
}