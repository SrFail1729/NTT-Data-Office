package com.example.nttdata.ui.screens.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.ui.theme.NttDataTheme

@Composable
fun MenuUsuarioScreen(
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onContactAdmin: () -> Unit
) {
    Scaffold(
        topBar = {
            HeaderUsuarioMenu(onBack)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Mi Perfil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF070F26),
                modifier = Modifier.padding(bottom = 32.dp).align(Alignment.Start)
            )

            OpcionMenuUsuario(
                text = "Modificar datos personales",
                icon = Icons.Default.Edit,
                onClick = onEditProfile
            )

            Spacer(Modifier.height(16.dp))

            OpcionMenuUsuario(
                text = "Contactar con Administrador",
                icon = Icons.Default.ContactSupport,
                onClick = onContactAdmin
            )
        }
    }
}

@Composable
fun OpcionMenuUsuario(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color(0xFF070F26)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF070F26)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuUsuarioScreenPreview() {
    NttDataTheme {
        MenuUsuarioScreen(onBack = {}, onEditProfile = {}, onContactAdmin = {})
    }
}
