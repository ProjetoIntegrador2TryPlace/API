package com.tryplace.tryplace.service

import com.tryplace.tryplace.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacaoService(
    private val usuarioRepository: UsuarioRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        
        val usuario = usuarioRepository.findByEmail(username)
        
        if (usuario != null) {
            return usuario
        }

        throw UsernameNotFoundException("Usuário não encontrado com o email informado")
    }
}