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
@Table(name = "locatario_db",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_locatario_email", columnNames = ["emailLocatario"]),
        UniqueConstraint(name = "uk_locatario_telefone", columnNames = ["telefoneLocatario"]),
        UniqueConstraint(name = "uk_locatario_email", columnNames = ["dataDeNascimentoLocatario"]),
    ]
)
class LocatarioModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    @Column(nullable = false)
    var nome: String,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false)
    var telefone: String,
    @Column(nullable = false)
    var dataDeNascimento: String,
    @CreationTimestamp
    var criadoEm: Instant = Instant.now()
)
