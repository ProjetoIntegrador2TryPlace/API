package com.tryplace.tryplace.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    @Value("\${app.mail.from}") private val remetente: String
) {

    private val logger = LoggerFactory.getLogger(EmailService::class.java)

    @Async
    fun enviarCodigoRecuperacao(emailDestino: String, codigo: String) {
        val mensagem = SimpleMailMessage().apply {
            setFrom(remetente)
            setTo(emailDestino)
            subject = "TryPlace - Recuperação de Senha"
            text = """
                Olá!
                
                Você solicitou a recuperação de senha na plataforma TryPlace.
                Use o código abaixo para redefinir sua senha:
                
                👉 $codigo
                
                Este código é válido por 15 minutos. Se você não solicitou essa alteração, ignore este e-mail.
                
                Atenciosamente,
                Equipe TryPlace.
            """.trimIndent()
        }

        try {
            mailSender.send(mensagem)
            logger.info("✅ Email de recuperação enviado para: $emailDestino")
        } catch (e: Exception) {
            logger.error("❌ Falha ao enviar email para $emailDestino: ${e.message}", e)
        }
    }
}