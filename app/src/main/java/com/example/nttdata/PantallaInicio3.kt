package com.example.nttdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun PantallaInicio3(
    citas: SnapshotStateList<CitaData>, // Lista mutable de citas que recibimos desde MainActivity
    modifier: Modifier = Modifier,
    onReservaClick: () -> Unit = {}, // Callback para navegar a la pantalla de reserva
    onBack: () -> Unit = {} // Callback para volver atrás (si fuera necesario)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()) // Respetamos los insets del sistema (barra de estado, etc.)
            .background(Color.White)

    ) {
        // Cabecera con información del usuario
        HeaderUsuario(onBack)

        Spacer(Modifier.height(24.dp))

        // ----------------------------------------------------------------
        // LISTA DE CITAS (LazyColumn)
        // ----------------------------------------------------------------
        // Usamos LazyColumn para que la lista sea eficiente y scrolleable
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa todo el espacio disponible verticalmente
        ) {
            // Iteramos sobre la lista de citas
            items(citas.size) { index ->
                CitaItem(citas[index]) // Renderizamos cada item
                Spacer(Modifier.height(1.dp))
            }
        }


        Spacer(Modifier.height(40.dp))

        // ----------------------------------------------------------------
        // BOTONES DE ACCIÓN (Imágenes como botones)
        // ----------------------------------------------------------------
        // Componente que contiene las imágenes para navegar a reservar
        GaleriaImagenes(
            onReservaClick = onReservaClick
        )

        Spacer(Modifier.height(24.dp))

        // Barra inferior decorativa
        BarraInferior()
    }
}


@Composable
fun HeaderUsuario(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26)), // Color de fondo azul oscuro corporativo
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
fun CitaItem(cita: CitaData) {
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

            // Botón para cancelar la cita (solo imprime por consola por ahora)
            OutlinedButton(
                onClick = { println("Cancelar cita: ${cita.fecha}") },
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
fun GaleriaImagenes(onReservaClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Tarjeta interactiva para "Reservar Puesto"
        androidx.compose.material3.Card(
            onClick = onReservaClick, // Ejecuta la navegación al hacer clic
            shape = RoundedCornerShape(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.reservar_puesto),
                contentDescription = "Reservar Puesto",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Tarjeta interactiva para "Reservar Sala" (sin implementación aún)
        androidx.compose.material3.Card(
            onClick = { /* TODO: Implementar reserva sala */ },
            shape = RoundedCornerShape(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.reservar_sala),
                contentDescription = "Reservar Sala",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun BarraInferior() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
            .padding(vertical = 13.dp, horizontal = 33.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween  // ← Clave
    ) {
        CoilImageWrapper(
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/ibjhmitd_expires_30_days.png",
            Modifier.size(35.dp)
        )

        CoilImageWrapper(
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/4vnd53eu_expires_30_days.png",
            Modifier.size(35.dp)
        )

        CoilImageWrapper(
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/m87eafkb_expires_30_days.png",
            Modifier.size(35.dp)
        )
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

@Preview(showBackground = true)
@Composable
fun PantallaInicio3Preview() {
    // Lista de ejemplo para el preview (estática)
    val citasDemo = listOf(
        CitaData(
            "25 Septiembre, Miércoles",
            "Oficina Castellón\n18:00 - 18:30",
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png"
        ),
        CitaData(
            "27 Septiembre, Viernes",
            "Oficina Castellón\n18:30 - 19:00",
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/0wyvh1nh_expires_30_days.png"
        )
    )

    // Convertimos la lista estática a SnapshotStateList solo para que compile
    val citasState = androidx.compose.runtime.snapshots.SnapshotStateList<CitaData>().apply {
        addAll(citasDemo)
    }

    PantallaInicio3(
        citas = citasState,
        onReservaClick = {},
        onBack = {}
    )
}