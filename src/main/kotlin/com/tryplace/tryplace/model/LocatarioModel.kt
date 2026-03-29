package com.tryplace.tryplace.model

import jakarta.persistence.Entity
import java.util.UUID

@Entity
data class LocatarioModel(val id: UUID, val nome: String, val email: String, val telefone: String, val dataDeNascimento: String)
