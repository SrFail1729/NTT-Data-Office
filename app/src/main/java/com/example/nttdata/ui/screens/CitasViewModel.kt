package com.example.nttdata.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.nttdata.domain.model.UserRole
import com.example.nttdata.domain.model.CitaData

class CitasViewModel : ViewModel() {

    private val _citas = mutableStateListOf<CitaData>()
    val citas: List<CitaData> = _citas

    // Estado del rol del usuario actual
    var currentUserRole by mutableStateOf(UserRole.WORKER)

    // Estado para el escáner de QR
    var showQrScanner by mutableStateOf(false)
    var citaAValidar by mutableStateOf<CitaData?>(null)

    fun anadirCita(cita: CitaData) {
        _citas.add(cita)
    }

    fun eliminarCita(cita: CitaData) {
        _citas.remove(cita)
    }

    fun iniciarValidacion(cita: CitaData) {
        citaAValidar = cita
        showQrScanner = true
    }

    fun cancelarValidacion() {
        showQrScanner = false
        citaAValidar = null
    }

    fun validarQr(scannedContent: String) {
        val currentCita = citaAValidar
        if (currentCita != null) {
            if (scannedContent == currentCita.targetQr) {
                // Buscamos la cita en la lista y la marcamos como confirmada
                val index = _citas.indexOf(currentCita)
                if (index != -1) {
                    _citas[index] = currentCita.copy(isConfirmed = true)
                }
                showQrScanner = false
                citaAValidar = null
            } else {
                // Aquí se podría mostrar un mensaje de error, por ahora solo imprimimos o dejamos que el usuario lo intente de nuevo
                println("QR incorrecto: $scannedContent vs ${currentCita.targetQr}")
            }
        }
    }
}
