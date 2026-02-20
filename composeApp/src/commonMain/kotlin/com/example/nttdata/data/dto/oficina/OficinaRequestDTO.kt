package com.example.nttdata.data.dto.oficina

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OficinaRequestDTO (
    @SerialName("nombre") val nombre: String,
    @SerialName("ciudad") val ciudad: String,
)