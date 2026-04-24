package com.tryplace.tryplace.controller

import com.tryplace.tryplace.model.UsuarioModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class MeResponse(val nome: String, val email: String, val tipo: String)

@RestController
@RequestMapping("/api/me")
class MeController {

    @GetMapping
    fun me(@AuthenticationPrincipal principal: Any?): ResponseEntity<MeResponse> {
        
        if (principal is UsuarioModel) {
            val response = MeResponse(
                nome = principal.nomeCompleto, 
                email = principal.email,
                tipo = principal.role
            )
            return ResponseEntity.ok(response)
        }
        
        return ResponseEntity.status(401).build()
    }
}