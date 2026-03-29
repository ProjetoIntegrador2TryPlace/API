package com.tryplace.tryplace.model

import jakarta.persistence.Entity
import java.util.UUID

@Entity
data class LocadorModel(val id: UUID, val nome: String, val email: String, val telefone: Number, val cnpj: String, val nomeEmpresa: String )
