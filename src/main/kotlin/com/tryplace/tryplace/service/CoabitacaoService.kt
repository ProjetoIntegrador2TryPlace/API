package com.tryplace.tryplace.service

import com.tryplace.tryplace.dto.PerfilCoabitacaoDetalheDto
import com.tryplace.tryplace.dto.PerfilCoabitacaoResumoDto
import com.tryplace.tryplace.exceptions.RecursoNaoEncontradoException
import com.tryplace.tryplace.exceptions.RegraDeNegocioException
import com.tryplace.tryplace.model.UsuarioModel
import com.tryplace.tryplace.repository.InteresseCoabitacaoRepository
import com.tryplace.tryplace.repository.UsuarioRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.util.UUID

@Service
class CoabitacaoService(
    private val usuarioRepository: UsuarioRepository,
    private val interesseRepository: InteresseCoabitacaoRepository,
    private val imovelService: ImovelService
) {

    fun listarPerfis(
        usuarioLogado: UsuarioModel,
        curso: String?,
        genero: String?,
        badge: String?,
        paginacao: Pageable
    ): Page<PerfilCoabitacaoResumoDto> {

        if (!usuarioLogado.interesseDividir) {
            throw RegraDeNegocioException("Você precisa habilitar seu perfil de coabitação para visualizar a comunidade.")
        }

        val idadeUsuario = Period.between(usuarioLogado.dataDeNascimento, LocalDate.now()).years
        val (idadeMinima, idadeMaxima) = if (idadeUsuario < 18) {
            Pair(null, 17)
        } else {
            Pair(18, null)
        }

        val perfis = usuarioRepository.buscarPerfisCoabitacao(
            requesterId = usuarioLogado.id!!,
            curso = curso,
            genero = genero,
            idadeMinima = idadeMinima,
            idadeMaxima = idadeMaxima,
            badge = badge,
            paginacao = paginacao
        )

        return perfis.map { u ->
            val idadeCalculada = Period.between(u.dataDeNascimento, LocalDate.now()).years
            
            // Força inicialização da coleção lazy e garante que não é null
            val badgesList = try {
                u.badges?.toList() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
            
            PerfilCoabitacaoResumoDto(
                id = u.id!!,
                nome = u.nomeCompleto,
                idade = idadeCalculada,
                cursoPeriodo = u.cursoPeriodo,
                badges = badgesList
            )
        }
    }

    fun buscarDetalhesPerfil(idDesejado: UUID, usuarioLogado: UsuarioModel): PerfilCoabitacaoDetalheDto {
        if (!usuarioLogado.interesseDividir) {
            throw RegraDeNegocioException("Acesso negado.")
        }

        val perfil = usuarioRepository.findById(idDesejado).orElseThrow {
            RecursoNaoEncontradoException("Perfil não encontrado.")
        }

        if (!perfil.interesseDividir) {
            throw RegraDeNegocioException("Este usuário desativou a busca por coabitação.")
        }

        val idadeUsuarioLogado = Period.between(usuarioLogado.dataDeNascimento, LocalDate.now()).years
        val idadePerfil = Period.between(perfil.dataDeNascimento, LocalDate.now()).years
        
        if (idadeUsuarioLogado < 18 && idadePerfil >= 18) {
            throw RegraDeNegocioException("Você só pode visualizar perfis de menores de idade.")
        }

        // Força inicialização das coleções lazy
        val badgesList = try {
            perfil.badges?.toList() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val interesses = interesseRepository.findAllByUsuario(perfil)
        val imoveisDto = interesses.map { imovelService.buscarImovelCadastradoPorId(it.imovel.id!!) }

        return PerfilCoabitacaoDetalheDto(
            id = perfil.id!!,
            nome = perfil.nomeCompleto,
            biografia = perfil.descricaoHabito,
            badges = badgesList,
            telefone = perfil.telefone,
            imoveisInteresse = imoveisDto
        )
    }

    fun alterarStatusCoabitacao(usuarioLogado: UsuarioModel, ativar: Boolean) {
        usuarioLogado.interesseDividir = ativar
        usuarioRepository.save(usuarioLogado)
    }
}