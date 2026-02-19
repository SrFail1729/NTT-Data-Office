package com.example.nttdata.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitasResponsDTO(
    @SerialName("token") val token: String,
    @SerialName("") val puesto: String,
    @SerialName("") val horaInicio: String,
    @SerialName("") val horaFin: String,
    @SerialName("") val estado: Boolean
)