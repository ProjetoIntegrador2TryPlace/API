package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.LocatarioDto
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.repository.LocatarioRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LocatarioService(
    private val repository: LocatarioRepository
) {

    fun buscarLocatario(nome: String) : LocatarioDto? {
        val entity = repository.findByNome(nome) ?: return null

        return LocatarioDto (
            id = entity.id!!,
            nome = entity.nome,
            email = entity.email,
            telefone = entity.telefone,
            dataDeNascimento = entity.dataDeNascimento
        )

    }


    fun criarLocatario(nome: String, email: String, telefone: String, dataDeNascimento: String, senha: String): LocatarioDto {
        val entity = LocatarioModel(
            nome = nome.trim(),
            email = email.trim(),
            telefone = telefone.trim(),
            dataDeNascimento = dataDeNascimento.trim(),
            senha = senha.trim()

        )
        val savedEntity = repository.save(entity)

        return LocatarioDto (
            id = savedEntity.id!!,
            nome = savedEntity.nome,
            email = savedEntity.email,
            telefone = savedEntity.telefone,
            dataDeNascimento = savedEntity.dataDeNascimento
        )
    }
}