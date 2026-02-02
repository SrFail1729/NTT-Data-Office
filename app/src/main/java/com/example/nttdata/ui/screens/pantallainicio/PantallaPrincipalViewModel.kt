package com.example.nttdata.ui.screens.pantallainicio

import androidx.lifecycle.ViewModel
import com.example.nttdata.domain.model.GestionSesion
import com.example.nttdata.domain.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PantallaPrincipalViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PantallaInicioUiState())
    val uiState : StateFlow<PantallaInicioUiState> = _uiState.asStateFlow()

    init {
        actualizarPantalla()
    }

    fun actualizarPantalla(){

        val nombre = GestionSesion.usuarioActual?.nombre ?: "Usuario"

        _uiState.update { estadoActual ->
            estadoActual.copy(usuario = nombre)
        }

    }
}