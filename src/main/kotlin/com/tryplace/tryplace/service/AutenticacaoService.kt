package com.tryplace.tryplace.service

import com.tryplace.tryplace.repository.LocadorRepository
import com.tryplace.tryplace.repository.LocatarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacaoService(
    private val locatarioRepository: LocatarioRepository,
    private val locadorRepository: LocadorRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val locatario = locatarioRepository.findByEmail(username)
        if (locatario != null) {
            return locatario
        }

        val locador = locadorRepository.findByEmail(username)
        if (locador != null) {
            return locador
        }

        throw UsernameNotFoundException("Usuário não encontrado com o email informado")
    }
}