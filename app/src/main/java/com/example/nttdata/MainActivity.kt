package com.example.nttdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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