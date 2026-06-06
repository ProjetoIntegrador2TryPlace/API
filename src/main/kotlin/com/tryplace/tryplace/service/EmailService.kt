package com.tryplace.tryplace.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    @Value("\${app.mail.from}") private val remetente: String
) {

    fun enviarCodigoRecuperacao(emailDestino: String, codigo: String) {
        val mensagem = SimpleMailMessage()

        mensagem.setFrom(remetente)
        mensagem.setTo(emailDestino)
        mensagem.setSubject("TryPlace - Recuperação de Senha")
        mensagem.setText(
            """
            Olá!
            
            Você solicitou a recuperação de senha na plataforma TryPlace.
            Use o código abaixo para redefinir sua senha:
            
            👉 $codigo
            
            Este código é válido por 15 minutos. Se você não solicitou essa alteração, ignore este e-mail.
            
            Atenciosamente,
            Equipe TryPlace.
            """.trimIndent()
        )

        try {
            mailSender.send(mensagem)
        } catch (e: Exception) {
            throw RuntimeException("Falha ao enviar o e-mail de recuperação: ${e.message}")
        }
    }
}