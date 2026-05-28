package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.GoogleGeocodingResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GeocodingService(
    @Value("\${google.maps.api.key}") private val apiKey: String
) {

    private val restTemplate = RestTemplate()

    fun buscarCoordenadas(endereco: String): Pair<Double, Double>? {
        try {
            val url = "https://maps.googleapis.com/maps/api/geocode/json?address={endereco}&key={key}"

            val resposta = restTemplate.getForObject(
                url,
                GoogleGeocodingResponse::class.java,
                endereco,
                apiKey
            )

            if (resposta != null && resposta.status == "OK" && resposta.results.isNotEmpty()) {
                val location = resposta.results[0].geometry.location
                return Pair(location.lat, location.lng)
            }

            println("Aviso: Endereço não encontrado ou limite de requisições da API atingido.")
            return null

        } catch (e: Exception) {
            println("Erro: Falha ao buscar coordenadas no Google Maps. Motivo: ${e.message}")
            return null
        }
    }
}