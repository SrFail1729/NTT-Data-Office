package com.example.nttdata.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.repository.AuthRepository
import com.example.nttdata.domain.model.GestionSesion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsuarioChanged(nuevoUsuario: String){
        _uiState.update { it.copy(usuario = nuevoUsuario) }
    }

    fun onContrasenyaChanged(nuevaContrasenya: String){
        _uiState.update { it.copy(contrasenya = nuevaContrasenya) }
    }

    fun onLoginClicked(usuario: String, contrasenya: String){
        if (usuario.isEmpty() || contrasenya.isEmpty()){
            _uiState.update { it.copy(mensaje = "El usuario o la contraseña estan vacíos") }
            return
        }

        viewModelScope.launch {

            val request = LoginRequestDTO(usuario,contrasenya)
            val result = repository.login(request)
            result.onSuccess {
                usuarioModel ->
                GestionSesion.usuarioActual = usuarioModel
                _uiState.update {
                    it.copy(
                        token = usuarioModel.token,
                        mensaje = "!Bienvenido ${usuarioModel.nombre}¡"
                    )
                }
            }.onFailure {
                error ->
                _uiState.update {
                    it.copy(mensaje = "Error: ${error.message}")
                }
            }
        }
    }

}