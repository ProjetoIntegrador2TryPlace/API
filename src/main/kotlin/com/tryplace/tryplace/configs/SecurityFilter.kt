package com.tryplace.tryplace.configs

import com.tryplace.tryplace.repository.LocadorRepository
import com.tryplace.tryplace.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.actuate.endpoint.SecurityContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class SecurityFilter (
    private val repository: LocadorRepository,
    private val tokenService: TokenService

): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenJWT = recuperarToken(request)

        if (tokenJWT != null) {
            val subject = tokenService.getSubject(tokenJWT)
            val usuario = repository.findByEmail(subject)

            if (usuario != null) {
                val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario?.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request,response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "")
        }

        return null
    }

}