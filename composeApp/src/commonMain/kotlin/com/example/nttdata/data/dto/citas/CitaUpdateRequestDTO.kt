package com.example.nttdata.data.dto.citas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitaUpdateRequestDTO (
    @SerialName("idRecurso") val idRecurso: Int? = null,
    @SerialName("entrada") val entrada: String? = null,
    @SerialName("salida") val salida: String? = null,
    @SerialName("validado") val validado: Boolean? = null
)