package com.tryplace.tryplace.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties



@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleGeocodingResponse(
    val status: String,
    val results: List<GoogleGeocodingResult>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleGeocodingResult(
    val geometry: GoogleGeometry
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleGeometry(
    val location: GoogleLocation
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleLocation(
    val lat: Double,
    val lng: Double
)