package com.tryplace.tryplace.controller

import com.tryplace.tryplace.service.RecuperacaoSenhaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recuperacao")
class RecuperacaoSenhaController(private val service: RecuperacaoSenhaService) {

    @PostMapping("/solicitar")
    fun solicitar(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        val codigoGerado = service.solicitarCodigo(email)

        return ResponseEntity.ok(mapOf(
            "mensagem" to "Código gerado com sucesso (modo local)",
            "codigo" to codigoGerado
        ))
    }

    @PostMapping("/redefinir")
    fun redefinir(@RequestParam codigo: String, @RequestParam novaSenha: String): ResponseEntity<Map<String, String>> {
        service.redefinirSenha(codigo, novaSenha)
        return ResponseEntity.ok(mapOf("mensagem" to "Senha redefinida com sucesso!"))
    }
}