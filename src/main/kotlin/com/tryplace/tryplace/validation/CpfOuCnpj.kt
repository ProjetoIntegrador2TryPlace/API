package com.tryplace.tryplace.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import kotlin.reflect.KClass


@ConstraintComposition(CompositionType.OR)
@CPF
@CNPJ
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [])
annotation class CpfOuCnpj(
    val message: String = "O documento deve ser um CPF ou CNPJ válido",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
