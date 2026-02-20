package com.example.nttdata.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    @SerialName("token") val token: String,
    @SerialName("id_usuario") val idUsuario: Long,
    @SerialName("nombre_completo") val nombre: String
)