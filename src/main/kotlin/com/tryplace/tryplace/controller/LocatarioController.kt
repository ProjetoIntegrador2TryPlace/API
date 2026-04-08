package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LocatarioDto
import com.tryplace.tryplace.dto.LocatarioRequest
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.service.LocatarioService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/locatario")
class LocatarioController(val locatarioService: LocatarioService) {


    @GetMapping("/{nome}")
    fun listarLocatario(@PathVariable nome: String) : LocatarioDto? {
        return locatarioService.buscarLocatario(nome)
    }

    @PostMapping("/registerLocatario")
    fun criarLocatario(@RequestBody locatario: LocatarioRequest) : LocatarioDto {
        val dto = locatarioService.criarLocatario(locatario.nome, locatario.email, locatario.telefone, locatario.dataDeNascimento, locatario.senha)
        return dto
    }



}