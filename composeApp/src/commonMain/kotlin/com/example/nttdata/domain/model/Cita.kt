package com.example.nttdata.domain.model

data class Cita(
    val id: String,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val recursoId: String,
    val isConfirmado: Boolean,
    val detalle: String = "",
    val iconUrl: String = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png",
    val targetQr: String = ""

)
