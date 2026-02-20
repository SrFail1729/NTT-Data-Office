package com.example.nttdata.data.dto.citas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitaResponseDTO(
    val id: Int,
    @SerialName("id_usuario") val idUsuario: Int,
    @SerialName("id_recurso") val idRecurso: Int,
    val entrada: String,
    val salida: String,
    val validad: Boolean
)