package com.example.nttdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nttdata.domain.model.UserRole
import com.example.nttdata.ui.components.BarraInferiorComun
import com.example.nttdata.ui.components.Menu
import com.example.nttdata.ui.screens.CitasViewModel
import com.example.nttdata.ui.screens.OficinaSelection.CrearOficina
import com.example.nttdata.ui.screens.OficinaSelection.OficinaViewModel
import com.example.nttdata.ui.screens.ReservaPuestos.ReservaPuestos
import com.example.nttdata.ui.screens.ReservaSalas.ReservaSalas
import com.example.nttdata.ui.screens.login.Login
import com.example.nttdata.ui.screens.pantallainicio.PantallaInicio
import com.example.nttdata.ui.theme.NttDataTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val citasViewModel: CitasViewModel = viewModel()
            val oficinaViewModel: OficinaViewModel = viewModel()

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
                                onLoginSuccess = { role ->
                                    // Guardamos el rol
                                    citasViewModel.currentUserRole = role
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
                                    navController.navigate("reservaSalas")
                                },
                                onBack = { navController.popBackStack() },
                                onMenuClick = {
                                    navController.navigate("Menu") // Navegamos a la pantalla Menu
                                }
                            )
                        }
                        composable("reservaPuestos") {
                            ReservaPuestos(
                                viewModel = citasViewModel,
                                oficinaViewModel = oficinaViewModel, // Inyectamos la instancia compartida
                                onBack = { navController.popBackStack() },
                                onMenuClick = {
                                    navController.navigate("Menu") 
                                }
                            )
                        }
                        composable("reservaSalas") {
                            ReservaSalas(
                                viewModel = citasViewModel,
                                oficinaViewModel = oficinaViewModel, // Inyectamos la instancia compartida
                                onBack = { navController.popBackStack() },
                                onMenuClick = {
                                    navController.navigate("Menu") 
                                }
                            )
                        }
                        composable("Menu") {
                            // Pasamos si es admin o no
                            val isAdmin = citasViewModel.currentUserRole == UserRole.ADMIN
                            Menu(
                                onBack = { navController.popBackStack() },
                                isAdmin = isAdmin,
                                onAddUserClick = { navController.navigate("anadirUsuario") },
                                onAddOfficeClick = { navController.navigate("crearOficina") }
                            )
                        }
                        composable("anadirUsuario") {
                           // Placeholder para añadir usuario
                           androidx.compose.material3.Text("Pantalla Añadir Usuario (En construcción)")
                        }
                        composable("crearOficina") {
                            // Aquí irá la nueva pantalla
                             CrearOficina(viewModel = oficinaViewModel, onBack = { navController.popBackStack() })
                            // androidx.compose.material3.Text("Pantalla Crear Oficina (En construcción)")
                        }
                    }
                }
            }
        }
    
}

@Composable
fun PantallaLogin(onLoginSuccess: (UserRole) -> Unit) {
    Login(onLoginSuccess = onLoginSuccess)
}
