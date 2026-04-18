package com.tryplace.tryplace.configs

import com.tryplace.tryplace.repository.LocadorRepository
import com.tryplace.tryplace.repository.LocatarioRepository
import com.tryplace.tryplace.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(
    private val tokenService: TokenService,
    private val locatarioRepository: LocatarioRepository,
    private val locadorRepository: LocadorRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val tokenJWT = recuperarToken(request)

    if (tokenJWT != null) {
        try {
            val subject = tokenService.getSubject(tokenJWT) // Se o token for inválido, cai no catch

            // 1. Tenta achar o usuário como Locatário primeiro
            val locatario = locatarioRepository.findByEmail(subject)
            if (locatario != null) {
                val authentication = UsernamePasswordAuthenticationToken(locatario, null, locatario.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                // 2. Se não achou como Locatário, tenta achar como Locador
                val locador = locadorRepository.findByEmail(subject)
                if (locador != null) {
                    val authentication = UsernamePasswordAuthenticationToken(locador, null, locador.authorities)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: Exception) {
            // Token expirou ou é inválido. Ignoramos e ele segue como Convidado!
            println("Token inválido ignorado: ${e.message}")
        }
    }
    filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "")
        }
        return null
    }
}