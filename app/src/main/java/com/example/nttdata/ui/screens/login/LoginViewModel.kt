package com.example.nttdata.ui.screens.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.local.SecurePreferences
import com.example.nttdata.data.repository.AuthRepository
import com.example.nttdata.domain.model.UserRole
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository: AuthRepository = AuthRepository()
    private val securePreferences = SecurePreferences(application)

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(securePreferences.rememberMe)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        if (rememberMe) {
            username = securePreferences.savedUsername ?: ""
        }
    }

    fun onLogin(onSuccess: (UserRole) -> Unit) {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Usuario y contraseña son requeridos"
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            val result = authRepository.login(
                LoginRequestDTO(
                    correo = username,
                    contrasena = password
                )
            )

            result.fold(
                onSuccess = { usuario ->
                    isLoading = false
                    
                    // Manejar Recordar usuario de forma segura
                    securePreferences.rememberMe = rememberMe
                    if (rememberMe) {
                        securePreferences.savedUsername = username
                    } else {
                        securePreferences.clearUsername()
                    }

                    // Map role locally for now as the API doesn't return it
                    val role = if (username.equals("admin", ignoreCase = true) || 
                                   username.equals("soltadmelapierna@empresa.com", ignoreCase = true) ||
                                   username.equals("ana.garcia@empresa.com", ignoreCase = true)) {
                        UserRole.ADMIN
                    } else {
                        UserRole.WORKER
                    }
                    onSuccess(role)
                },
                onFailure = { exception ->
                    isLoading = false
                    errorMessage = exception.message ?: "Error desconocido"
                }
            )
        }
    }
}
