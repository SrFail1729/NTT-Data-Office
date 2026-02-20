package com.example.nttdata.ui.screens.pantallainicio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nttdata.data.local.GestorSesion
import com.example.nttdata.domain.model.Cita
import com.example.nttdata.domain.model.UserRole
import com.example.nttdata.domain.repository.CitaRepository
import kotlinx.coroutines.launch

class CitasViewModel(
    private val sesion: GestorSesion,
    private val repository: CitaRepository
) : ViewModel() {

    private val _citas = mutableStateListOf<Cita>()
    val citas: List<Cita> = _citas

    // Estado del rol del usuario actual
    var currentUserRole by mutableStateOf(UserRole.WORKER)

    // Estado para el escáner de QR
    var showQrScanner by mutableStateOf(false)
    var citaAValidar by mutableStateOf<Cita?>(null)

    fun anadirCita(cita: Cita) {
        _citas.add(cita)
    }

    fun eliminarCita(cita: Cita) {
        _citas.remove(cita)
    }

    fun iniciarValidacion(cita: Cita) {
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
                    _citas[index] = currentCita.copy(isConfirmado = true)
                }
                showQrScanner = false
                citaAValidar = null
            } else {
                // Aquí se podría mostrar un mensaje de error, por ahora solo imprimimos o dejamos que el usuario lo intente de nuevo
                println("QR incorrecto: $scannedContent vs ${currentCita.targetQr}")
            }
        }
    }

    private fun verificarSesionCargarla(){
        viewModelScope.launch {

        }
    }
}