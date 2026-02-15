package com.example.nttdata.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.example.nttdata.data.dto.request.LoginRequestDTO // Removed unused import? No, it's used.
import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.local.SecurePreferences
import com.example.nttdata.data.repository.AuthRepository
import com.example.nttdata.domain.model.UserRole
import kotlinx.coroutines.launch

class LoginViewModel(
    private val securePreferences: SecurePreferences
) : ViewModel() {
    private val authRepository: AuthRepository = AuthRepository()

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
            println("LoginViewModel: Iniciando login...")
            val result = authRepository.login(
                LoginRequestDTO(
                    correo = username,
                    contrasena = password
                )
            )
            println("LoginViewModel: Resultado recibido: $result")

            result.fold(
                onSuccess = { usuario ->
                    println("LoginViewModel: Login exitoso")
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
                    println("LoginViewModel: Login fallido: ${exception.message}")
                    exception.printStackTrace()
                    isLoading = false
                    errorMessage = exception.message ?: "Error desconocido"
                }
            )
        }
    }
}
