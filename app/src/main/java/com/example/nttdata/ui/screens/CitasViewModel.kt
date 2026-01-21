package com.example.nttdata.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CitasViewModel : ViewModel() {

    private val _citas = mutableStateListOf<CitaData>()
    val citas: List<CitaData> = _citas

    fun anadirCita(cita: CitaData) {
        _citas.add(cita)
    }

    fun eliminarCita(cita: CitaData) {
        _citas.remove(cita)
    }
}
