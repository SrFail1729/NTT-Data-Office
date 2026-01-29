package com.example.nttdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nttdata.ui.theme.components.BarraInferiorComun
import com.example.nttdata.ui.theme.components.Menu
import com.example.nttdata.ui.theme.screens.login.Login
import com.example.nttdata.ui.theme.screens.PantallaInicio
import com.example.nttdata.ui.theme.screens.ReservaPuestos
import com.example.nttdata.ui.theme.screens.ReservaSalas
import com.example.nttdata.ui.theme.NttDataTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val citasViewModel: CitasViewModel = viewModel()

            NttDataTheme {
                NavHost(
                    navController = navController,
                    startDestination = "login" // Pantalla inicial
                ) {
                    composable("BarraInferior") {
                        BarraInferiorComun(
                            onMenuClick = {
                                navController.navigate("Menu") // Navegamos a la pantalla Menu
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("login") {
                        PantallaLogin(
                            onLoginSuccess = {
                                // Al loguearse, navegamos a la pantalla de inicio
                                navController.navigate("pantallaInicio") {
                                    // Opcional: Eliminar login del backstack para no volver con "atrás"
                                    // popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("pantallaInicio") {
                        PantallaInicio(
                            viewModel = citasViewModel,   // Pasamos la lista compartida
                            onReservaPuestoClick = {
                                // Navegamos a la pantalla de reserva
                                navController.navigate("reservaPuestos")
                            },
                            onReservaSalaClick = {
                                // Navegamos a la pantalla de reserva
                                navController.navigate("reservaPuestos")
                            },
                            onBack = { navController.popBackStack() },
                            onMenuClick = {
                                navController.navigate("Menu") // Navegamos a la pantalla Menu
                            }
                        )
                    }
                    composable("reservaPuestos") {
                        ReservaPuestos(
                            viewModel = citasViewModel, // Pasamos la misma lista para poder añadir citas
                            onBack = { navController.popBackStack() }, // Volver atrás
                            onMenuClick = {
                                navController.navigate("Menu") // Navegamos a la pantalla Menu
                            }
                        )
                    }
                    composable("reservaSalas") {
                        ReservaSalas(
                            viewModel = citasViewModel, // Pasamos la misma lista para poder añadir citas
                            onBack = { navController.popBackStack() }, // Volver atrás
                            onMenuClick = {
                                navController.navigate("Menu") // Navegamos a la pantalla Menu
                            }
                        )
                    }
                    composable("Menu") {
                        Menu(
                            onBack = { navController.popBackStack() } // Volver atrás
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaLogin(onLoginSuccess: () -> Unit) {
    Login(onLoginSuccess = onLoginSuccess)
}