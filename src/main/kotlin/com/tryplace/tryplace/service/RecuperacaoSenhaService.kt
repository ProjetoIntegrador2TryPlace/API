package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.TokenRecuperacaoSenhaModel
import com.tryplace.tryplace.repository.TokenRecuperacaoSenhaRepository
import com.tryplace.tryplace.repository.UsuarioRepository
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.MimeMessageHelper
import kotlin.random.Random

@Service
class RecuperacaoSenhaService(
    private val usuarioRepository: UsuarioRepository,
    private val tokenRepository: TokenRecuperacaoSenhaRepository,
    private val mailSender: JavaMailSender,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun solicitarCodigo(email: String) {
        val usuario = usuarioRepository.findByEmail(email) ?: throw RuntimeException("E-mail não encontrado")

        val codigo = Random.nextInt(100000, 999999).toString()
        val token = TokenRecuperacaoSenhaModel(
            codigoToken = codigo,
            dataExpiracao = LocalDateTime.now().plusMinutes(15),
            usuario = usuario
        )
        tokenRepository.save(token)

        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        helper.setFrom("TryPlace <elpedrorenan08@gmail.com>")
        helper.setTo(email)
        helper.setSubject("$codigo é o seu código de recuperação")

        val htmlBody = """
            <div style="font-family: sans-serif; max-width: 400px; margin: 0 auto; border: 1px solid #e1e1e1; padding: 20px; border-radius: 10px;">
                <h2 style="color: #333; text-align: center;">Recuperação de Senha</h2>
                <p style="color: #555;">Você solicitou a redefinição de senha no <strong>TryPlace</strong>. Use o código abaixo para continuar:</p>
                <div style="background-color: #f4f4f4; padding: 15px; text-align: center; border-radius: 8px; margin: 20px 0;">
                    <span style="font-size: 32px; font-weight: bold; letter-spacing: 5px; color: #2c3e50;">$codigo</span>
                </div>
                <p style="font-size: 12px; color: #888; text-align: center;">Este código expira em 15 minutos.</p>
            </div>
        """.trimIndent()

        helper.setText(htmlBody, true)
        mailSender.send(mimeMessage)
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