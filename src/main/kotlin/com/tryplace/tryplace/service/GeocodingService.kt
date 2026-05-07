package com.tryplace.tryplace.service

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GeocodingService {

    private val restTemplate = RestTemplate()

    fun buscarCoordenadas(endereco: String): Pair<Double, Double>? {
        try {
            val url = "https://nominatim.openstreetmap.org/search?format=json&q={endereco}&limit=1"

            val headers = HttpHeaders()
            headers.set("User-Agent", "TryPlaceApp/1.0 (elpedrorenan08@gmail.com)")

            val entity = HttpEntity<String>(headers)

            val typeRef = object : ParameterizedTypeReference<List<Map<String, Any>>>() {}

            val resposta = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                typeRef,
                endereco
            )

            val corpo = resposta.body

            if (!corpo.isNullOrEmpty()) {
                val local = corpo[0]
                val lat = (local["lat"] as String).toDouble()
                val lon = (local["lon"] as String).toDouble()
                return Pair(lat, lon)
            }

            return null

        } catch (e: Exception) {
            println("Aviso: Falha ao buscar coordenadas na API de mapas. Motivo: ${e.message}")
            return null
        }
    }
}