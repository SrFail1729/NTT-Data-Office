package com.example.nttdata.data.dto.response

import com.google.gson.annotations.SerializedName

//Representación de los datos en la aplicación
data class LoginResponseDTO(
    @SerializedName("token") val token: String,
    @SerializedName("id_usuario") val idUsuario: Long,
    @SerializedName("nombre_completo") val nombre: String
)
