package com.tryplace.tryplace.controller

import com.tryplace.tryplace.service.RecuperacaoSenhaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recuperacao")
class RecuperacaoSenhaController(private val service: RecuperacaoSenhaService) {

    @PostMapping("/solicitar")
    fun solicitar(@RequestParam email: String) = service.solicitarCodigo(email)

    @PostMapping("/redefinir")
    fun redefinir(@RequestParam codigo: String, @RequestParam novaSenha: String) =
        service.redefinirSenha(codigo, novaSenha)
}