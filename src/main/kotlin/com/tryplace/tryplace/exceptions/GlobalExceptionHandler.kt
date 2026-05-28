package com.tryplace.tryplace.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException

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


        @ExceptionHandler(RegraDeNegocioException::class)
        fun handlerRegraDeNegocio(ex: RegraDeNegocioException): ResponseEntity<Any> {
            val body = mapOf(
                "timestamp" to LocalDateTime.now(),
                "status" to HttpStatus.BAD_REQUEST.value(),
                "error" to "Violação de Regra de Negócio",
                "message" to ex.message
            )
            return ResponseEntity(body, HttpStatus.BAD_REQUEST)
        }

        @ExceptionHandler(MethodArgumentNotValidException::class)
        fun handlerValidationExceptions(ex : MethodArgumentNotValidException): ResponseEntity<Any> {
            val errosDeCampo = ex.bindingResult.fieldErrors.associate {
                it.field to it.defaultMessage
            }

            val body = mapOf(
                "timestamp" to LocalDateTime.now(),
                "status" to HttpStatus.BAD_REQUEST.value(),
                "error" to "Erro de validação dos campos",
                "message" to "Um ou mais campos estão inválidos",
                "campos" to errosDeCampo
            )
            return ResponseEntity(body, HttpStatus.BAD_REQUEST)
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

        @ExceptionHandler(BadCredentialsException::class)
        fun handleBadCredentials(ex: BadCredentialsException): ResponseEntity<Any> {
            val body = mapOf(
                "timestamp" to LocalDateTime.now(),
                "status" to HttpStatus.UNAUTHORIZED.value(),
                "error" to "Não autorizado",
                "message" to "E-mail ou senha incorretos."
            )
            return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
        }

        @ExceptionHandler(AuthenticationException::class)
        fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<Any> {
            val body = mapOf(
                "timestamp" to LocalDateTime.now(),
                "status" to HttpStatus.UNAUTHORIZED.value(),
                "error" to "Falha na autenticação",
                "message" to ex.message
            )
            return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
        }
}