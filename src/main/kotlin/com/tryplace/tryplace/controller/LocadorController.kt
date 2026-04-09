package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.LocadorDto
import com.tryplace.tryplace.dto.LocadorRequest
import com.tryplace.tryplace.service.LocadorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/locador")
class LocadorController(
    private val locadorService: LocadorService
){
    @PostMapping("/registrarLocador")
    fun criarLocador(locador: LocadorRequest): LocadorDto {
        return locadorService.criarLocador(locador)
    }

    @GetMapping("/{nome}")
    fun buscarLocador(@PathVariable nome: String): LocadorDto? {
        return locadorService.buscarLocador(nome)
    }
}
