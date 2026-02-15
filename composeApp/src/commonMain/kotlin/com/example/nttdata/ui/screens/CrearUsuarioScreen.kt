package com.example.nttdata.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.ui.components.HeaderReserva
import com.example.nttdata.ui.theme.NttDataTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearUsuarioScreen(
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            HeaderReserva(onBack, title = "Añadir Usuario")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Información del Usuario",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF070F26),
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF070F26),
                    focusedLabelColor = Color(0xFF070F26),
                    cursorColor = Color(0xFF070F26)
                )
            )

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF070F26),
                    focusedLabelColor = Color(0xFF070F26),
                    cursorColor = Color(0xFF070F26)
                )
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo Corporativo") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF070F26),
                    focusedLabelColor = Color(0xFF070F26),
                    cursorColor = Color(0xFF070F26)
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF070F26),
                    focusedLabelColor = Color(0xFF070F26),
                    cursorColor = Color(0xFF070F26)
                )
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Crear Usuario", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview
@Composable
fun CrearUsuarioScreenPreview() {
    NttDataTheme {
        CrearUsuarioScreen(onBack = {}, onSubmit = {})
    }
}
