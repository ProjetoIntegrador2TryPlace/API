package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.LocatarioDto
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.repository.LocatarioRepository
import org.springframework.stereotype.Service

@Service
class LocatarioService(
    private val repository: LocatarioRepository
) {
    fun criarLocatario(nome: String, email: String, telefone: String, dataDeNascimento: String): LocatarioDto {
        val entity = LocatarioModel(
            nome = nome.trim(),
            email = email.trim(),
            telefone = telefone.trim(),
            dataDeNascimento = dataDeNascimento.trim(),
        )
        repository.save(entity)

        return LocatarioDto (

        )
    }
}