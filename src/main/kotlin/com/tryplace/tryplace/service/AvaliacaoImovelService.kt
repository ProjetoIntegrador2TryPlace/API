package com.tryplace.tryplace.service

import com.tryplace.tryplace.model.AvaliacaoImovelModel
import com.tryplace.tryplace.repository.AvaliacaoImovelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Pageable
import java.util.UUID

@Service
class AvaliacaoImovelService(
    private val avaliacaoImovelRepository: AvaliacaoImovelRepository

) {
    @Transactional
    fun criarAvaliacaoImovel(avaliadorId: UUID, imovelId: UUID, nota: Int, comentarioRaw: String?): AvaliacaoImovelModel {
        if (avaliacaoImovelRepository.existsByAvaliadorIdAndImovelId(avaliadorId, imovelId)) {
            throw RuntimeException("Usuário já avaliou este imovel")
        }

        val comentarioFiltrado = aplicarFiltroDePalavras(comentarioRaw)

        val novaAvaliacao = AvaliacaoImovelModel(
            avaliadorId = avaliadorId,
            imovelId = imovelId,
            nota = nota,
            comentario = comentarioFiltrado
        )

        return avaliacaoImovelRepository.save(novaAvaliacao)
    }

    fun listarAvaliacoesImovel(imovelId: UUID, pageable: Pageable): List<AvaliacaoImovelModel>{
        return avaliacaoImovelRepository.findByImovelIdOrderByDataCriacaoDesc(imovelId, pageable).content
    }

    fun calcularMediaAvaliacoes(imovelId: UUID): Double {
        val avaliacoes = avaliacaoImovelRepository.findByImovelId(imovelId)
        return if (avaliacoes.isNotEmpty()) {
            avaliacoes.map { it.nota }.average()
        } else {
            0.0
        }
    }

    fun contarAvaliacoes(imovelId: UUID): Int {
        return avaliacaoImovelRepository.findByImovelId(imovelId).size
    }

    fun jaAvaliou(avaliadorId: UUID, imovelId: UUID): Boolean {
        return avaliacaoImovelRepository.existsByAvaliadorIdAndImovelId(avaliadorId, imovelId)
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