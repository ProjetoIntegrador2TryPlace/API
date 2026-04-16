package com.tryplace.tryplace.service

import com.tryplace.tryplace.repository.LocatarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LocatarioAutenticacao(private val repository: LocatarioRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = repository.findByEmail(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado com o email")

        return usuario
    }
}