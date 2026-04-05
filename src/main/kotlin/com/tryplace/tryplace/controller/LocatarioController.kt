package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LocatarioDto
import com.tryplace.tryplace.dto.LocatarioRequest
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.service.LocatarioService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/locatario")
class LocatarioController(val locatarioService: LocatarioService) {


//    @GetMapping
//    fun listarLocatario() : List<LocatarioModel> {
//        return listarLocatario()
//    }

    @PostMapping("/registerLocatario")
    fun criarLocatario(@RequestBody locatario: LocatarioRequest) : LocatarioDto {
        return LocatarioDto(id="123",nome = locatario.nome, email = locatario.email, telefone = locatario.telefone, dataDeNascimento = locatario.dataDeNascimento)
    }



}