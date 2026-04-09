package com.tryplace.tryplace.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.util.UUID
import java.time.Instant

@Entity
@Table(name = "locador_db")
data class LocadorModel(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @Column(nullable = false )
    var nome: String,
    @Column(nullable = true)
    var nomeImobiliaria: String? = null,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false, unique = true)
    var telefone: String,
    @Column(nullable = false, unique = true)
    var cnpj: String,
    @Column(nullable = false)
    var senha: String,
    @CreationTimestamp
    var criadoEm: Instant? = null

)
