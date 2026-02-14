package com.example.nttdata.domain.model

data class CitaData(
    val fecha: String,
    val detalle: String,
    val iconUrl: String,
    val targetQr: String = "",
    var isConfirmed: Boolean = false
)
