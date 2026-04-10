package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.LocadorDto
import com.tryplace.tryplace.dto.LocadorRequest
import com.tryplace.tryplace.model.LocadorModel
import com.tryplace.tryplace.repository.LocadorRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class LocadorService(
    private val repository: LocadorRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun criarLocador(requestlocador: LocadorRequest) : LocadorDto {

        val senhaBruta = requestlocador.senha
        val senhaCriptografada = passwordEncoder.encode(senhaBruta)

        val entidade = LocadorModel (
            nome = requestlocador.nome.trim(),
            nomeImobiliaria = requestlocador.nomeImobiliaria?.trim(),
            email = requestlocador.email.trim(),
            telefone = requestlocador.telefone.trim(),
            cnpj = requestlocador.cnpj.trim(),
            senha = senhaCriptografada

        )
        val savedEntidade = repository.save(entidade)

        return LocadorDto (
            id = savedEntidade.id!!,
            nome = savedEntidade.nome,
            nomeImobiliaria = savedEntidade.nomeImobiliaria,
            email = savedEntidade.email,
            telefone = savedEntidade.telefone,
            cnpj = savedEntidade.cnpj
            )
    }

    fun buscarLocador(nome: String): LocadorDto? {

        val entity = repository.findByNome(nome) ?: return null

        return LocadorDto (
            id = entity.id!!,
            nome = entity.nome,
            nomeImobiliaria = entity.nomeImobiliaria,
            email = entity.email,
            telefone = entity.telefone,
            cnpj = entity.cnpj
        )
    }
}