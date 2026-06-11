package com.tryplace.tryplace.controller

import com.tryplace.tryplace.service.RecuperacaoSenhaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recuperacao")
class RecuperacaoSenhaController(private val service: RecuperacaoSenhaService) {

    @PostMapping("/solicitar")
    fun solicitar(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        service.solicitarCodigo(email)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                mapOf(
                    "mensagem" to "Código de verificação foi enviado com sucesso"
                )
            )
    }

    @PostMapping("/redefinir")
    fun redefinir(
        @RequestParam codigo: String,
        @RequestParam novaSenha: String
    ): ResponseEntity<Map<String, String>> {
        service.redefinirSenha(codigo, novaSenha)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                mapOf(
                    "mensagem" to "Senha redefinida com sucesso!"
                )
            )
    }
}