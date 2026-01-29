package com.example.nttdata

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.nttdata.ui.theme.screens.CitaData

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
