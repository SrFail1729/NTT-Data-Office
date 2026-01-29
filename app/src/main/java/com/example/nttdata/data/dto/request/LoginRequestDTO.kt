package com.example.nttdata.data.dto.request

import com.google.gson.annotations.SerializedName

data class LoginRequestDTO(
    @SerializedName("correo_corporativo") val correo: String,
    @SerializedName("contrasena") val constrasenya: String
)
