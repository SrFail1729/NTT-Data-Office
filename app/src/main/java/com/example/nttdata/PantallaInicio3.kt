package com.example.nttdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier,
    onReservaClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(21.dp)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.safeDrawing.asPaddingValues())  // <-- ESTE
            .background(Color.White)

    ) {

        HeaderUsuario()
        Spacer(Modifier.height(24.dp))

        /* Citas del usuario */
        val citas = listOf(
            CitaData(
                "25 Septiembre, Miércoles", "Oficina Castellón\n18:00 - 18:30",
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png"
            ),
            CitaData(
                "27 Septiembre, Viernes", "Oficina Castellón\n18:30 - 19:00",
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/0wyvh1nh_expires_30_days.png"
            )
        )

        citas.forEach {
            CitaItem(it)
            Spacer(Modifier.height(24.dp))
        }

        UltimaCita(
            fecha = "28 Septiembre, Viernes",
            iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/vcdci61c_expires_30_days.png"
        )

        Spacer(Modifier.height(40.dp))

        /* 🔵 Navega a ReservaPuestos7 */
        GaleriaImagenes(
            onReservaClick = onReservaClick
        )

        Spacer(Modifier.height(24.dp))
        BarraInferior()
    }
}


@Composable
fun HeaderUsuario() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImageWrapper(
            imageUrl = "https://cdn.pixabay.com/photo/2023/02/18/11/00/icon-7797704_1280.png",
            modifier = Modifier
                .size(78.dp)
                .padding(end = 4.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Usuario de prueba",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class CitaData(val fecha: String, val detalle: String, val iconUrl: String)

@Composable
fun CitaItem(cita: CitaData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFD9D9D9))
            .padding(9.dp)
    ) {
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(cita.detalle, color = Color(0xFF070F26))
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
fun UltimaCita(fecha: String, iconUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFD9D9D9))
            .padding(vertical = 9.dp, horizontal = 9.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fecha, color = Color(0xFF070F26))
        CoilImageWrapper(
            iconUrl, modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(5.dp))
        )
    }
}

@Composable
fun GaleriaImagenes(onReservaClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.reservar_puesto),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clickable { onReservaClick() }    // ← NAVEGA A RESERVA
        )

        Image(
            painter = painterResource(id = R.drawable.reservar_puesto),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
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
    PantallaInicio3()
}
