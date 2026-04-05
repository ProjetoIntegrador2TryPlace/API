package com.tryplace.tryplace.repository

import com.tryplace.tryplace.model.LocatarioModel
import org.hibernate.validator.constraints.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocatarioRepository: JpaRepository<LocatarioModel, UUID>{
}