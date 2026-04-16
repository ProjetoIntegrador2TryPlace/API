package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.LocatarioDto
import com.tryplace.tryplace.dto.LocatarioRequest
import com.tryplace.tryplace.exceptions.RecursoNaoEncontradoException
import com.tryplace.tryplace.model.LocatarioModel
import com.tryplace.tryplace.repository.LocatarioRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LocatarioService(
    private val repository: LocatarioRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun criarLocatario(request: LocatarioRequest): LocatarioDto {
        val senhaBruta: String = request.senha
        val senhaCriptografada = passwordEncoder.encode(senhaBruta)

        val entity = LocatarioModel(
            nome = request.nome.trim(),
            email = request.email.trim(),
            telefone = request.telefone.trim(),
            dataDeNascimento = request.dataDeNascimento.trim(),
            senha = senhaCriptografada

        )
        val savedEntity = repository.save(entity)

        return LocatarioDto(
            id = savedEntity.id!!,
            nome = savedEntity.nome,
            email = savedEntity.email,
            telefone = savedEntity.telefone,
            dataDeNascimento = savedEntity.dataDeNascimento
        )
    }

    fun buscarLocatario(nome: String) : LocatarioDto {
        val entity = repository.findByNome(nome)
            ?: throw RecursoNaoEncontradoException("Locatário com o $nome não encontrado")

        return LocatarioDto (
            id = entity.id!!,
            nome = entity.nome,
            email = entity.email,
            telefone = entity.telefone,
            dataDeNascimento = entity.dataDeNascimento
        )

    }

}