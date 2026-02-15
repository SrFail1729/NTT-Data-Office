package com.example.nttdata.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.imagen_generica

@Composable
fun HeaderComun(
    onBack: () -> Unit,
    title: String = "Reserva Puestos"
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 2.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(18.dp))

        AvatarUsuario(
            Res.drawable.imagen_generica,
            tamanyo = 40.dp,
            modifier = Modifier.padding(1.dp)
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(title, color = Color.White, fontSize = 22.sp)
    }
}
