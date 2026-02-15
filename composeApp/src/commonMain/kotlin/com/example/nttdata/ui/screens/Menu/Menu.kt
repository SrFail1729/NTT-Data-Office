package com.example.nttdata.ui.screens.Menu

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.ui.components.BarraInferiorComun
import com.example.nttdata.ui.components.CoilImageWrapper
import com.example.nttdata.ui.theme.NttDataTheme


@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    isAdmin: Boolean = false,
    onAddUserClick: () -> Unit = {},
    onAddOfficeClick: () -> Unit = {}
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
                .fillMaxSize()

        ) {

            Spacer(Modifier.height(24.dp))
            
            // Opciones de Administrador
            if (isAdmin) {
                Text(
                    text = "Panel de Administrador",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 24.dp, bottom = 16.dp)
                )

                OpcionMenuAdmin(text = "Añadir Usuario", onClick = onAddUserClick)
                OpcionMenuAdmin(text = "Añadir Oficina", onClick = onAddOfficeClick)
            }
        }
    }
}

@Composable
fun OpcionMenuAdmin(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF070F26)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text, fontSize = 16.sp, color = Color.White)
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
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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


@Preview
@Composable
fun MenuPreview() {
    NttDataTheme {
        Menu(
            onBack = {},
            isAdmin = true
        )
    }
}
