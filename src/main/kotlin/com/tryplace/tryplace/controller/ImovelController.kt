package com.tryplace.tryplace.controller

import com.tryplace.tryplace.dto.ImovelDto
import com.tryplace.tryplace.dto.ImovelRequest
import com.tryplace.tryplace.repository.ImovelRepository
import com.tryplace.tryplace.service.ImovelService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/imovel")
class ImovelController(
    private val service: ImovelService
) {

    @PostMapping
    fun criarImovel(@RequestBody request: ImovelRequest): ImovelDto {
        return service.criarImovel(request)
    }

//    @GetMapping
//    fun listarImovel() {
//        return service.listarImovel()
//    }
//
//    @PutMapping
//    fun editarImovel() {
//        return service.editarImovel()
//    }

    @DeleteMapping("/{id}")
    fun deletarImovel (@PathVariable id: UUID) {
        service.deleteImovel(id)
    }

}