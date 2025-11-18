package com.example.nttdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PantallaInicio3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 21.dp, vertical = 24.dp)
    ) {
        HeaderUsuario()

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de citas
        val citas = listOf(
            CitaData(
                "25 Septiembre, Miércoles",
                "Oficina de Castellón\n18h00 - 18h30",
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png"
            ),
            CitaData(
                "27 Septiembre, Viernes",
                "Oficina de Castellón\n18h30 - 19h00",
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/0wyvh1nh_expires_30_days.png"
            )
        )
        citas.forEach { cita ->
            CitaItem(cita)
            Spacer(modifier = Modifier.height(24.dp))
        }

        UltimaCita(
            fecha = "28 Septiembre, Viernes",
            iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/vcdci61c_expires_30_days.png"
        )

        Spacer(modifier = Modifier.height(24.dp))

        GaleriaImagenes(
            listOf(
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/erv6dsre_expires_30_days.png",
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/7789iqgl_expires_30_days.png"
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        BarraInferior()
    }
}

@Composable
fun HeaderUsuario() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
            .padding(horizontal = 33.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImageWrapper(
            imageUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/mc1d90dw_expires_30_days.png",
            modifier = Modifier
                .size(78.dp)
                .padding(end = 24.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Usuario de prueba",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Usuario de prueba",
                color = Color.White,
                fontSize = 26.sp,
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
                modifier = Modifier.size(24.dp).clip(RoundedCornerShape(5.dp))
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
        CoilImageWrapper(iconUrl, modifier = Modifier.size(24.dp).clip(RoundedCornerShape(5.dp)))
    }
}

@Composable
fun GaleriaImagenes(imagenes: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(45.dp)
    ) {
        imagenes.forEach { url ->
            CoilImageWrapper(url, modifier = Modifier
                .height(138.dp)
                .weight(1f))
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImageWrapper(
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/ibjhmitd_expires_30_days.png",
            Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        CoilImageWrapper(
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/4vnd53eu_expires_30_days.png",
            Modifier
                .size(35.dp)
                .padding(end = 110.dp)
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
