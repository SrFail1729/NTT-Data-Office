package com.example.nttdata.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.domain.model.CitaData
import com.example.nttdata.ui.components.HeaderReserva
import com.example.nttdata.ui.theme.NttDataTheme

@Composable
fun DetalleCitaScreen(
    cita: CitaData,
    onBack: () -> Unit
) {
    var checkPantalla by remember { mutableStateOf(true) }
    var checkRaton by remember { mutableStateOf(true) }
    var checkTeclado by remember { mutableStateOf(true) }
    var checkAuriculares by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            HeaderReserva(onBack, title = "Detalle de la Cita")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Sección de Información de la Cita
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Información de la Reserva",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF070F26)
                    )
                    Divider(color = Color.LightGray)
                    
                    InfoRow(label = "Fecha:", value = cita.fecha)
                    InfoRow(label = "Detalle:", value = cita.detalle)
                    InfoRow(
                        label = "Estado:", 
                        value = if (cita.isConfirmed) "Confirmada" else "Pendiente de Confirmar",
                        valueColor = if (cita.isConfirmed) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                    )
                }
            }

            // Sección de Recursos
            Text(
                text = "Recursos que se van a utilizar:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF070F26)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
                    .padding(8.dp)
            ) {
                ResourceItem(label = "Pantalla", isChecked = checkPantalla, onCheckedChange = { checkPantalla = it })
                ResourceItem(label = "Ratón", isChecked = checkRaton, onCheckedChange = { checkRaton = it })
                ResourceItem(label = "Teclado", isChecked = checkTeclado, onCheckedChange = { checkTeclado = it })
                ResourceItem(label = "Auriculares", isChecked = checkAuriculares, onCheckedChange = { checkAuriculares = it })
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Volver", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, valueColor: Color = Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.Bold, color = valueColor)
    }
}

@Composable
fun ResourceItem(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF070F26))
        )
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF070F26)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetalleCitaScreenPreview() {
    NttDataTheme {
        DetalleCitaScreen(
            cita = CitaData(
                fecha = "14/02/2026",
                detalle = "Reserva de Puesto 12 - Oficina Central",
                iconUrl = "",
                isConfirmed = false
            ),
            onBack = {}
        )
    }
}
