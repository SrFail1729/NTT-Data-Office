package com.example.nttdata.data.dto.citas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitaCreateRequestDTO(
    @SerialName("idUsuario") val idUsuario: Int,
    @SerialName("idRecurso") val idRecurso: Int,
    @SerialName("salida") val salida: String,
    @SerialName("entrada") val entrada: String
)