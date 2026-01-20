package com.example.nttdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.components.BarraInferiorComun

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}, // Callback para volver atrás (si fuera necesario)
    onMenuClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            HeaderUsuarioMenu(onBack) // Cabecera con información del usuario
        },
        bottomBar = {
            BarraInferiorComun(
                onMenuClick = {},
                onBack = onBack
            ) // Barra inferior
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding) //  Ajusta el contenido
                .background(Color.White)

        ) {

            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
fun HeaderUsuarioMenu(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26)) // Color de fondo azul oscuro corporativo
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón de retroceso estándar
        androidx.compose.material3.IconButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        // Imagen de perfil del usuario
        CoilImageWrapper(
            imageUrl = "https://cdn.pixabay.com/photo/2023/02/18/11/00/icon-7797704_1280.png",
            modifier = Modifier
                .size(78.dp)
                .padding(start = 2.dp, end = 7.dp, top = 5.dp, bottom = 5.dp)
                .clip(CircleShape) // Recorte circular
        )
        // Nombre del usuario
        Text(
            "Usuario de prueba",
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    Menu(
        onBack = {}
    )
}