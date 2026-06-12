package com.tryplace.tryplace.controller

import com.tryplace.tryplace.service.GeocodingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/geocode")
class GeocodingController(
    private val geocodingService: GeocodingService
) {

    @GetMapping("/testar")
    fun testarGeocoding(@RequestParam endereco: String): ResponseEntity<Any> {
        val coordenadas = geocodingService.buscarCoordenadas(endereco)

        return if (coordenadas != null) {
            ResponseEntity.ok(mapOf(
                "latitude" to coordenadas.first,
                "longitude" to coordenadas.second
            ))
        } else {
            ResponseEntity.badRequest().body("Não foi possível encontrar as coordenadas.")
        }
    }
}