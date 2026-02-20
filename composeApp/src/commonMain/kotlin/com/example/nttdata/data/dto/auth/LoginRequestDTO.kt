package com.example.nttdata.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(
    @SerialName("correo_corporativo") val correo: String,
    @SerialName("contrasena") val contrasenya: String
)
