package com.tryplace.tryplace.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

    @RestControllerAdvice
    class GlobalExceptionHandler {

        @ExceptionHandler(RecursoNaoEncontradoException::class)
        fun handleRecursoNaoEncontrado(ex: RecursoNaoEncontradoException): ResponseEntity<Any> {
            val body = mapOf(
                "timestamp" to LocalDateTime.now(),
                "status" to HttpStatus.NOT_FOUND.value(),
                "error" to "Recurso não encontrado",
                "message" to ex.message
            )
            return ResponseEntity(body, HttpStatus.NOT_FOUND)
        }

        @ExceptionHandler(Exception::class)
        fun handleGenericException(ex: Exception): ResponseEntity<Any> {
            val body = mapOf(
                "timestamp" to LocalDateTime.now(),
                "status" to HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error" to "Erro interno do servidor",
                "message" to ex.message
            )
            return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
        }
}