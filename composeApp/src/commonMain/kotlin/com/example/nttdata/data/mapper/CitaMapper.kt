package com.example.nttdata.data.mapper

import com.example.nttdata.data.dto.citas.CitaResponseDTO
import com.example.nttdata.domain.model.Cita

fun CitaResponseDTO.toDomain(): Cita{

    val partesEntrada = this.entrada.split(" ")
    val fechaOriginal = partesEntrada[0]
    val horaEntrada = partesEntrada[1].substring(0,5)

    val horaSalida = this.salida.split(" ")[1].substring(0,5)

    return Cita(
        id = this.id.toString(),
        fecha = formatearFecha(fechaOriginal),
        horaInicio = horaEntrada,
        horaFin = horaSalida,
        recursoId = "Recursos #${this.idRecurso}",
        isConfirmado = this.validad
    )
}

private fun formatearFecha(fecha: String): String{
    val partes = fecha.split("-")
    return "${partes[2]}/${partes[1]}/${partes[0]}"
}