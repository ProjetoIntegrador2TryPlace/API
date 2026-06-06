package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.TokenRecuperacaoSenhaModel
import com.tryplace.tryplace.repository.TokenRecuperacaoSenhaRepository
import com.tryplace.tryplace.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class RecuperacaoSenhaService(
    private val usuarioRepository: UsuarioRepository,
    private val tokenRepository: TokenRecuperacaoSenhaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService
) {

    @Transactional
    fun solicitarCodigo(email: String) {
        val usuario = usuarioRepository.findByEmail(email)
            ?: throw RuntimeException("E-mail não encontrado")

        val codigo = Random.nextInt(100000, 999999).toString()

        val token = TokenRecuperacaoSenhaModel(
            codigoToken = codigo,
            dataExpiracao = LocalDateTime.now().plusMinutes(15),
            usuario = usuario
        )
        tokenRepository.save(token)

        emailService.enviarCodigoRecuperacao(email, codigo)
    }

    @Transactional
    fun redefinirSenha(codigo: String, novaSenhaLimpa: String) {
        val token = tokenRepository.findByCodigoToken(codigo)
            .filter { !it.isExpirado() }
            .orElseThrow { RuntimeException("Código inválido ou expirado") }

        val usuario = token.usuario
        usuario.senha = passwordEncoder.encode(novaSenhaLimpa)
        usuarioRepository.save(usuario)

        tokenRepository.delete(token)
    }
}