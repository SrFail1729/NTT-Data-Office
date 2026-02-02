package com.example.nttdata.ui.screens.login

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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nttdata.ui.theme.NttDataTheme

@Composable
fun Login(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    val loginUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(loginUiState.token) {
        if (loginUiState.token.isNotEmpty()) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.safeDrawing.asPaddingValues())
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

            // --- SECCIÓN DE INPUTS ---
            Column(modifier = Modifier.fillMaxWidth()) {

                // Campo Usuario
                TextField(
                    value = loginUiState.usuario,
                    onValueChange = { viewModel.onUsuarioChanged(it) },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color(0xFF0073BD)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo Contraseña
                TextField(
                    value = loginUiState.contrasenya,
                    onValueChange = { viewModel.onContrasenyaChanged(it) },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(), // Oculta los caracteres
                    trailingIcon = {
                        AsyncImage(
                            model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/m43ql7uo_expires_30_days.png",
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color(0xFF0073BD)
                    ),
                    singleLine = true
                )
            }

            // Mostrar mensaje de error o éxito si existe
            if (loginUiState.mensaje.isNotEmpty()) {
                Text(
                    text = loginUiState.mensaje,
                    color = if (loginUiState.token.isEmpty()) Color.Red else Color(0xFF2E7D32),
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // --- BOTÓN DE ACCIÓN ---
            Button(
                onClick = {
                    viewModel.onLoginClicked(loginUiState.usuario, loginUiState.contrasenya)
                },
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
            // ... resto del código (olvidé contraseña, etc)
        }
    }
}