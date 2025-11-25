package com.example.nttdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nttdata.ui.theme.NttDataTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño de borde a borde (detrás de la barra de estado)

        setContent {
            // Controlador de navegación principal
            val navController = rememberNavController()

            // ----------------------------------------------------------------
            // ESTADO COMPARTIDO (State Hoisting)
            // ----------------------------------------------------------------
            // Creamos la lista de citas aquí para que sobreviva a la navegación
            // y pueda ser compartida entre pantallas (Inicio y Reserva)
            val citas = remember { mutableStateListOf<CitaData>() }


            NttDataTheme {
                // ----------------------------------------------------------------
                // GRAFO DE NAVEGACIÓN
                // ----------------------------------------------------------------
                NavHost(
                    navController = navController,
                    startDestination = "login" // Pantalla inicial
                ) {

                    // --- PANTALLA DE LOGIN ---
                    composable("login") {
                        PantallaLogin(
                            onLoginSuccess = {
                                // Al loguearse, navegamos a la pantalla de inicio
                                navController.navigate("pantallaInicio3") {
                                    // Opcional: Eliminar login del backstack para no volver con "atrás"
                                    // popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // --- PANTALLA DE INICIO ---
                    composable("pantallaInicio3") {
                        PantallaInicio3(
                            citas = citas,   // Pasamos la lista compartida
                            onReservaClick = {
                                // Navegamos a la pantalla de reserva
                                navController.navigate("reservaPuestos7")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // --- PANTALLA DE RESERVA ---
                    composable("reservaPuestos7") {
                        ReservaPuestos7(
                            citas = citas, // Pasamos la misma lista para poder añadir citas
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
    Greeting(onLoginSuccess = onLoginSuccess)
}


@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.safeDrawing.asPaddingValues())  // <-- ESTE
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/iyyl20fd_expires_30_days.png",
                contentDescription = null,
                modifier = Modifier.size(111.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "NTT DATA Office",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    "Usuario",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
                Divider(color = Color.Black, thickness = 1.dp)

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    "Contraseña",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 8.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    AsyncImage(
                        model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/m43ql7uo_expires_30_days.png",
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onLoginSuccess,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    text = "Inicio de sesión",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Has olvidado tu contraseña?",
                color = Color(0xFF0073BD),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Si no tienes cuenta, solicítala",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NttDataTheme {
        Greeting()
    }
}