package com.example.nttdata

import androidx.compose.runtime.Composable
import com.example.nttdata.ui.screens.login.Login
import com.example.nttdata.ui.theme.NttDataTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.example.nttdata.data.local.SecurePreferences


import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nttdata.di.DataGraph
import com.example.nttdata.domain.model.UserRole
import com.example.nttdata.ui.screens.pantallainicio.PantallaInicio
import com.example.nttdata.ui.screens.pantallainicio.CitasViewModel
import com.example.nttdata.ui.screens.ReservaPuestos.ReservaPuestos
import com.example.nttdata.ui.screens.ReservaSalas.ReservaSalas
import com.example.nttdata.ui.screens.Menu.MenuUsuarioScreen
import com.example.nttdata.ui.screens.DetalleCitaScreen
import com.example.nttdata.ui.screens.CrearUsuarioScreen
import com.example.nttdata.ui.screens.OficinaSelection.OficinaViewModel
import com.example.nttdata.ui.screens.OficinaSelection.CrearOficina
import com.example.nttdata.ui.theme.AppearanceViewModel
import com.example.nttdata.ui.screens.Menu.AppearanceScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Enum para gestionar las pantallas de la aplicación
enum class Screen {
    LOGIN,
    HOME,
    RESERVA_PUESTOS,
    RESERVA_SALAS,
    MENU,
    APPEARANCE,
    DETALLE_CITA,
    EDIT_PROFILE,
    ADD_USER,
    ADD_OFFICE
}

@Composable
fun App(securePreferences: SecurePreferences) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).build()
    }

    val scope = rememberCoroutineScope()
    val gestorSesion = remember { DataGraph.gestorSesionPublico }

    val idUsuarioPersistente by gestorSesion.idUsuario.collectAsState(initial = null)

    // Estado de navegación
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
    var userRole by remember { mutableStateOf<UserRole?>(null) }
    var selectedCitaIndex by remember { mutableStateOf<Int?>(null) }
    var isAuthComprobacion by remember { mutableStateOf(false) }

    // ViewModels compartidos entre pantallas
    val citasViewModel = remember { CitasViewModel(
        sesion = gestorSesion,
        repository = DataGraph.citaRepository
    ) }

    val oficinaViewModel = remember { OficinaViewModel() }
    val appearanceViewModel = remember { AppearanceViewModel() }

    LaunchedEffect(Unit) {
        val idGuardado = gestorSesion.idUsuario.first()
        val tieneToken = securePreferences.authToken != null

        if (idGuardado != null && tieneToken) {
            currentScreen = Screen.HOME
        } else {
            currentScreen = Screen.LOGIN
        }
        isAuthComprobacion = true
    }

    LaunchedEffect(idUsuarioPersistente) {
        if (isAuthComprobacion && idUsuarioPersistente == null) {
            currentScreen = Screen.LOGIN
            userRole = null
        }
    }

    NttDataTheme(
        darkTheme = appearanceViewModel.isDarkMode,
        fontScale = appearanceViewModel.fontScale
    ) {
        when (currentScreen) {
            Screen.LOGIN -> {
                Login(
                    securePreferences = securePreferences,
                    onLoginSuccess = { role ->
                        userRole = role
                        currentScreen = Screen.HOME
                    }
                )
            }
            
            Screen.HOME -> {
                PantallaInicio(
                    viewModel = citasViewModel,
                    onReservaSalaClick = { 
                        currentScreen = Screen.RESERVA_SALAS 
                    },
                    onReservaPuestoClick = { 
                        currentScreen = Screen.RESERVA_PUESTOS 
                    },
                    onReservaClick = { index ->
                        selectedCitaIndex = index
                        currentScreen = Screen.DETALLE_CITA
                    },
                    onBack = { 
                        // Volver al login (cerrar sesión)
                        scope.launch {
                            gestorSesion.logout()
                            securePreferences.logout()
                            userRole = null
                            currentScreen = Screen.LOGIN
                        }
                    },
                    onMenuClick = { 
                        currentScreen = Screen.MENU 
                    }
                )
            }
            
            Screen.RESERVA_PUESTOS -> {
                ReservaPuestos(
                    viewModel = citasViewModel,
                    oficinaViewModel = oficinaViewModel,
                    onBack = { currentScreen = Screen.HOME },
                    onMenuClick = { currentScreen = Screen.MENU }
                )
            }
            
            Screen.RESERVA_SALAS -> {
                ReservaSalas(
                    viewModel = citasViewModel,
                    oficinaViewModel = oficinaViewModel,
                    onBack = { currentScreen = Screen.HOME },
                    onMenuClick = { currentScreen = Screen.MENU }
                )
            }
            
            Screen.MENU -> {
                MenuUsuarioScreen(
                    onBack = { currentScreen = Screen.HOME },
                    onEditProfile = { 
                        currentScreen = Screen.EDIT_PROFILE
                    },
                    onAppearanceClick = {
                        currentScreen = Screen.APPEARANCE
                    },
                    onContactAdmin = { 
                        // TODO: Implementar contacto con admin
                    },
                    isAdmin = userRole == UserRole.ADMIN,
                    onAddUserClick = {
                        currentScreen = Screen.ADD_USER
                    },
                    onAddOfficeClick = {
                        currentScreen = Screen.ADD_OFFICE
                    }
                )
            }

            Screen.APPEARANCE -> {
                AppearanceScreen(
                    viewModel = appearanceViewModel,
                    onBack = { currentScreen = Screen.MENU }
                )
            }
            
            Screen.EDIT_PROFILE -> {
                CrearUsuarioScreen(
                    onBack = { currentScreen = Screen.MENU },
                    onSubmit = { 
                        // Guardar cambios y volver al menú
                        currentScreen = Screen.MENU
                    }
                )
            }
            
            Screen.ADD_USER -> {
                CrearUsuarioScreen(
                    onBack = { currentScreen = Screen.MENU },
                    onSubmit = {
                        // TODO: Implementar creación de usuario
                        currentScreen = Screen.MENU
                    }
                )
            }
            
            Screen.ADD_OFFICE -> {
                CrearOficina(
                    viewModel = oficinaViewModel,
                    onBack = { currentScreen = Screen.MENU }
                )
            }
            
            Screen.DETALLE_CITA -> {
                selectedCitaIndex?.let { index ->
                    if (index < citasViewModel.citas.size) {
                        DetalleCitaScreen(
                            cita = citasViewModel.citas[index],
                            onBack = { currentScreen = Screen.HOME }
                        )
                    } else {
                        // Si el índice no es válido, volver a HOME
                        currentScreen = Screen.HOME
                    }
                } ?: run {
                    // Si no hay índice seleccionado, volver a HOME
                    currentScreen = Screen.HOME
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    val mockSecurePreferences = object : SecurePreferences {
        override var rememberMe: Boolean = false
        override var savedUsername: String? = null
        override var authToken: String?
            get() = TODO("Not yet implemented")
            set(value) {}

        override fun clearUsername() {}
        override fun logout() {
            TODO("Not yet implemented")
        }
    }
    App(securePreferences = mockSecurePreferences)
}
