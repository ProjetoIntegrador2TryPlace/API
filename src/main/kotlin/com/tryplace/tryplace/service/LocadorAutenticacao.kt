package com.tryplace.tryplace.service

import com.tryplace.tryplace.repository.LocadorRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LocadorAutenticacao(private val repository: LocadorRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = repository.findByEmail(username)
        ?: throw UsernameNotFoundException("Usuario com o email não encontrado")

        return usuario
    }

}