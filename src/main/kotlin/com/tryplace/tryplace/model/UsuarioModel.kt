package com.tryplace.tryplace.model

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "usuario_tb",)
class UsuarioModel (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @Column(nullable = false)
    var nomeCompleto: String,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false, unique = true)
    var cpfCnpj: String,
    @Column(nullable = false)
    var telefone: String,
    @Column(nullable = false)
    var dataDeNascimento: LocalDate,
    @Column(nullable = false)
    var senha: String,

    // campos que vão ser usados em momentos especificos

    var nomeEmpresa: String? = null,
    var cursoPeriodo: String? = null,
    var interesseDividir: Boolean = false,

    //regras para pessoas que são menores de 18 anos

    var nomeResponsavel: String? = null,
    var cpfResponsavel: String? = null,
    var ipConsentimento: String? = null,

    @Column(length = 500)
    var descricaoHabito: String? = null,
    var genero: String? = null,
    @Column(nullable = false)
    var termoResponsabilidade: Boolean = false,

    @Column(nullable = false)
    var role: String = "ROLE_USUARIO",

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "usuario_badges", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "badge")
    var badges: MutableList<String> = mutableListOf(),

) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String = senha

    override fun getUsername(): String = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}