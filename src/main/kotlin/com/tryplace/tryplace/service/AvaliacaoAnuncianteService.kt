package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.AvaliacaoAnuncianteModel
import com.tryplace.tryplace.repository.AvaliacaoAnuncianteRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AvaliacaoAnuncianteService(
    private val avaliacaoAnuncianteRepository: AvaliacaoAnuncianteRepository
) {

    @Transactional
    fun criarAvaliacao(avaliadorId: UUID, anuncianteId: UUID, nota: Int, comentarioRaw: String?): AvaliacaoAnuncianteModel {

        if (avaliacaoAnuncianteRepository.existsByAvaliadorIdAndAnuncianteId(avaliadorId, anuncianteId)) {
            throw RuntimeException("Usuário já avaliou este anunciante.")
        }

        val comentarioFiltrado = aplicarFiltroDePalavras(comentarioRaw)

        val novaAvaliacao = AvaliacaoAnuncianteModel(
            avaliadorId = avaliadorId,
            anuncianteId = anuncianteId,
            nota = nota,
            comentario = comentarioFiltrado
        )

        return avaliacaoAnuncianteRepository.save(novaAvaliacao)
    }

    fun listarAvaliacoes(anuncianteId: UUID, pageable: Pageable): List<AvaliacaoAnuncianteModel> {
        return avaliacaoAnuncianteRepository.findByAnuncianteIdOrderByDataCriacaoDesc(anuncianteId, pageable).content
    }

    fun calcularMediaAvaliacoes(anuncianteId: UUID): Double {
        val avaliacoes = avaliacaoAnuncianteRepository.findByAnuncianteId(anuncianteId)
        return if (avaliacoes.isNotEmpty()) {
            avaliacoes.map { it.nota }.average()
        } else {
            0.0
        }
    }

    fun contarAvaliacoes(anuncianteId: UUID): Int {
        return avaliacaoAnuncianteRepository.findByAnuncianteId(anuncianteId).size
    }

    fun jaAvaliou(avaliadorId: UUID, anuncianteId: UUID): Boolean {
        return avaliacaoAnuncianteRepository.existsByAvaliadorIdAndAnuncianteId(avaliadorId, anuncianteId)
    }

    private fun aplicarFiltroDePalavras(texto: String?): String? {
        if (texto.isNullOrBlank()) return texto

        var textoLimpo: String = texto

        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        textoLimpo = emailRegex.replace(textoLimpo, "[E-MAIL OCULTO]")

        val telefoneRegex = Regex("\\b(?:\\+?55\\s?)?(?:\\(?\\d{2}\\)?\\s?)?\\d{4,5}[-\\s]?\\d{4}\\b")
        textoLimpo = telefoneRegex.replace(textoLimpo, "[TELEFONE OCULTO]")

        val palavroes = listOf("palavrao1", "palavrao2", "idiota", "imbecil")

        for (palavrao in palavroes) {
            textoLimpo = textoLimpo.replace(Regex(palavrao, RegexOption.IGNORE_CASE), "***")
        }

        return textoLimpo
    }
}