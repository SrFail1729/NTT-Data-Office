package com.example.nttdata

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ReservaPuestos7(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())  // <-- ESTE
            .background(Color.White)

    ) {

        HeaderReserva(onBack)

        Spacer(Modifier.height(16.dp))
        OficinaSelector(
            "Valencia",
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/vjlbfl27_expires_30_days.png"
        )

        Spacer(Modifier.height(16.dp))
        HoraSelector()

        Spacer(Modifier.height(16.dp))
        PlanoOficina()

        Spacer(Modifier.height(24.dp))
        BarraInferiorReserva()
    }
}

@Composable
fun HeaderReserva(onBack: () -> Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "<",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .clickable { onBack() }
                .padding(start = 15.dp, end = 16.dp)
                .size(25.dp)
        )
        Text("Reserva Puestos", color = Color.White, fontSize = 22.sp)
    }
}


@Composable
fun OficinaSelector(nombre: String, iconUrl: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 44.dp, end = 29.dp)
    ) {
        Text(
            "Oficina:",
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 16.dp)
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF070F26))
                .weight(1f)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "    $nombre",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 33.dp)
            )
            AsyncImage(
                model = iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 15.dp)
            )
        }
    }
}

@Composable
fun HoraSelector() {
    // Ejemplo de selector de hora con dos botones
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            "Pick your time",
            color = Color(0xFF070F26),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        ReservaHoraBoton("19:00")
        ReservaHoraBoton("19:30")
    }
}

@Composable
fun ReservaHoraBoton(texto: String) {
    OutlinedButton(
        onClick = { println("Pressed hora $texto") },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFF070F26)),
        modifier = Modifier
            .height(38.dp)
            .width(110.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(texto, color = Color(0xFF070F26), fontSize = 18.sp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanoOficina() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(22.dp)
    ) {
        // Imagen de fondo
        AsyncImage(
            model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/0dkjijlz_expires_30_days.png",
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Cuadrícula de mesas
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // número de columnas
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(6) { // número de imágenes
                Image(
                    painter = painterResource(id = R.drawable.mesa),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }
        }
    }
}

@Composable
fun BarraInferiorReserva() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
            .padding(vertical = 13.dp, horizontal = 33.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/69bgb9nb_expires_30_days.png",
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/er6acdl3_expires_30_days.png",
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(end = 110.dp)
        )
        AsyncImage(
            model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/wy6fvds1_expires_30_days.png",
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ReservaPuestos7Preview() {
    ReservaPuestos7()
}
