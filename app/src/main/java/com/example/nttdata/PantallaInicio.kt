package com.example.nttdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nttdata.components.BarraInferiorComun


@Composable
fun PantallaInicio(
    viewModel: CitasViewModel,
    modifier: Modifier = Modifier,
    onReservaSalaClick: () -> Unit = {},
    onReservaPuestoClick: () -> Unit = {},
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            HeaderUsuario(onBack)
        },
        bottomBar = {
            BarraInferiorComun(
                onMenuClick = onMenuClick,
                onBack = onBack
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)

        ) {

            Spacer(Modifier.height(24.dp))

            // Usamos LazyColumn para que la lista sea eficiente y scrolleable
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(viewModel.citas) { cita ->
                    CitaItem(
                        cita = cita,
                        onDelete = { viewModel.eliminarCita(it) }
                    )
                }
            }
            Spacer(Modifier.height(40.dp))

            // Componente que contiene las imágenes para navegar a reservar
            GaleriaImagenes(
                onReservaPuestoClick = onReservaPuestoClick,
                onReservaSalaClick = onReservaSalaClick
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
fun HeaderUsuario(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
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

// Modelo de datos para una cita
data class CitaData(val fecha: String, val detalle: String, val iconUrl: String)

@Composable
fun CitaItem(
    cita: CitaData,
    onDelete: (CitaData) -> Unit
) {
    // Tarjeta visual para cada cita
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(21.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFD9D9D9)) // Fondo gris claro
            .padding(9.dp)
    ) {
        // Fila superior: Fecha e icono
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(cita.fecha, color = Color(0xFF070F26))
            CoilImageWrapper(
                imageUrl = cita.iconUrl,
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Fila inferior: Detalle y botón de cancelar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(cita.detalle, color = Color(0xFF070F26))

            // Botón para cancelar (elimina la cita)
            OutlinedButton(
                onClick = { onDelete(cita) },
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF070F26)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.height(38.dp)
            ) {
                Text(
                    "Cancelar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun GaleriaImagenes(onReservaPuestoClick: () -> Unit, onReservaSalaClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Tarjeta interactiva para "Reservar Puesto"
        androidx.compose.material3.Card(
            onClick = onReservaPuestoClick,
            shape = RoundedCornerShape(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.reservar_puesto),
                contentDescription = "Reservar Puesto",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )
        }

        // Tarjeta interactiva para "Reservar Sala"
        androidx.compose.material3.Card(
            onClick = onReservaSalaClick,
            shape = RoundedCornerShape(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.reservar_sala),
                contentDescription = "Reservar Sala",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CoilImageWrapper(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}