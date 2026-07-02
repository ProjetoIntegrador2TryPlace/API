package com.tryplace.tryplace.configs

import com.tryplace.tryplace.repository.UsuarioRepository
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
    private val usuarioRepository: UsuarioRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenJWT = recuperarToken(request)

        if (tokenJWT != null) {
            try {
                val subject = tokenService.getSubject(tokenJWT)
                val usuario = usuarioRepository.findByEmail(subject)

                if (usuario != null) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.authorities
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (ex: RuntimeException) {
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        return authorizationHeader?.replace("Bearer ", "")
    }
}