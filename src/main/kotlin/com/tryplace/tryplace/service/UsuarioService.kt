package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.AtualizarPerfilRequest
import com.tryplace.tryplace.dto.UsuarioDto
import com.tryplace.tryplace.dto.UsuarioRequest
import com.tryplace.tryplace.exceptions.RecursoNaoEncontradoException
import com.tryplace.tryplace.exceptions.RegraDeNegocioException
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.util.UUID

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {

    private fun mascararDocumento(doc : String?): String? {
        if (doc.isNullOrBlank()) return null

        val limpo = doc.filter { it.isDigit() }

        return when (limpo.length) {
            11 -> "***.${limpo.substring(3,6)}.${limpo.substring(6,9)}-**"
            14 -> "**.${limpo.substring(2, 5)}.${limpo.substring(5, 8)}/****-**"
            else -> doc
        }
    }

    fun buscarPerfil(id: UUID): UsuarioDto {
        val usuario = repository.findById(id).orElseThrow {
            RecursoNaoEncontradoException("Usuário não encontrado")
        }
        return converterParaDto(usuario)
    }

    fun atualizarPerfil(usuarioLogado: UsuarioModel, request: AtualizarPerfilRequest): UsuarioDto {
        val usuario = repository.findById(usuarioLogado.id!!).orElseThrow {
            RecursoNaoEncontradoException("Usuário não encontrado")
        }

        request.cpfCnpj?.let {
            val documentoLimpo = it.filter { char -> char.isDigit() }

            if (usuario.cpfCnpj != documentoLimpo && repository.existsByCpfCnpj(documentoLimpo)) {
                throw RuntimeException("Este CPF/CNPJ já está em uso por outra conta.")
            }
            usuario.cpfCnpj = documentoLimpo
        }

        request.nomeEmpresa?.let { usuario.nomeEmpresa = it }
        request.cursoPeriodo?.let { usuario.cursoPeriodo = it }
        request.interesseDividir?.let { usuario.interesseDividir = it }

        request.descricaoHabito?.let { usuario.descricaoHabito = it }
        request.genero?.let { usuario.genero = it }
        request.termoResponsabilidade?.let { usuario.termoResponsabilidade = it }

        request.badges?.let { novasBadges ->
            usuario.badges.clear()
            usuario.badges.addAll(novasBadges)
        }

        val usuarioAtualizado = repository.save(usuario)
        return converterParaDto(usuarioAtualizado)
    }


    fun cadastrarUsuario(request: UsuarioRequest, ipCliente: String): UsuarioDto {
        if (repository.existsByEmail(request.email)) {
            throw RegraDeNegocioException("E-mail já cadastrado no sistema.")
        }
        if (repository.existsByCpfCnpj(request.cpfCNPJ)) {
            throw RuntimeException("CPF ou CNPJ já cadastrado no sistema.")
        }

        val idade = Period.between(request.dataDeNascimento, LocalDate.now()).years

        if (idade < 18) {
            if (request.nomeResponsavel.isNullOrBlank() || request.cpfResponsavel.isNullOrBlank()) {
                throw RuntimeException("Usuários menores de idade devem informar Nome e CPF do Responsável.")
            }
        }

        val novoUsuario = UsuarioModel(
            nomeCompleto = request.nomeCompleto,
            email = request.email,
            cpfCnpj = request.cpfCNPJ.filter { it.isDigit() },
            telefone = request.telefone,
            dataDeNascimento = request.dataDeNascimento,
            senha = passwordEncoder.encode(request.senha),
            nomeResponsavel = if (idade < 18) request.nomeResponsavel else null,
            cpfResponsavel = if (idade < 18) request.cpfResponsavel else null,
            ipConsentimento = ipCliente
        )

        val usuarioSalvo = repository.save(novoUsuario)
        return converterParaDto(usuarioSalvo)
    }

    private fun converterParaDto(usuario: UsuarioModel): UsuarioDto {
        val idade = Period.between(usuario.dataDeNascimento, LocalDate.now()).years
        val docLimpo = usuario.cpfCnpj.filter { it.isDigit() }
        val tipo = if (docLimpo.length == 14) "EMPRESA" else "PESSOA FISICA"

        return UsuarioDto(
            id = usuario.id!!,
            nomeCompleto = usuario.nomeCompleto,
            email = usuario.email,
            cpfCnpj = mascararDocumento(usuario.cpfCnpj) ?: usuario.cpfCnpj,
            telefone = usuario.telefone,
            dataDeNascimento = usuario.dataDeNascimento,
            isMaiorDeIdade = idade >= 18,
            tipoConta = tipo,
            nomeEmpresa = usuario.nomeEmpresa,
            cursoPeriodo = usuario.cursoPeriodo,
            interesseDividir = usuario.interesseDividir,
            descricaoHabito = usuario.descricaoHabito,
            genero = usuario.genero,
            badges = usuario.badges.toList()
        )
    }
}