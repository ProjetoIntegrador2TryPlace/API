package com.tryplace.tryplace.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.CreationTimestamp
import java.util.UUID
import java.time.Instant

@Entity
@Table(name = "locatario_db")

class LocatarioModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @Column(nullable = false, unique = true)
    var nome: String,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false, unique = true)
    var telefone: String,
    @Column(nullable = false)
    var dataDeNascimento: String,
    @Column(nullable = false)
    var senha: String,
    @CreationTimestamp
    var criadoEm: Instant? = null
)
