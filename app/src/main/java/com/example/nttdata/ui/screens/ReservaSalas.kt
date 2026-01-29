package com.example.nttdata.ui.theme.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nttdata.CitasViewModel
import com.example.nttdata.R
import com.example.nttdata.ui.theme.components.BarraInferiorComun
import com.example.nttdata.ui.theme.components.DateTimeSelectionDialogs
import com.example.nttdata.ui.theme.components.DateTimeSelectors
import com.example.nttdata.ui.theme.components.HeaderReserva
import com.example.nttdata.ui.theme.components.rememberDateTimeSelectionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaSalas(
    viewModel: CitasViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    var selectedCity by remember { mutableStateOf("Castellón") }

    val dateTimeState = rememberDateTimeSelectionState()

    Scaffold(
        topBar = {
            HeaderReserva(onBack, title = "Reserva Salas")
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {

            Spacer(Modifier.height(10.dp))

            OficinaSelector(
                selectedCity = selectedCity,
                onCitySelected = { selectedCity = it }
            )


            Spacer(Modifier.height(10.dp))

            // Usamos el componente compartido para los selectores
            DateTimeSelectors(state = dateTimeState)

            Spacer(Modifier.height(10.dp))

            // ----------------------------------------------------------------
            // BOTÓN DE CONFIRMACIÓN
            // ----------------------------------------------------------------
            Button(
                onClick = {
                    // Validamos que se haya seleccionado una fecha antes de guardar
                    if (dateTimeState.dateMillis != null) {
                        // Añadimos la nueva cita a la lista compartida
                        viewModel.anadirCita(
                            CitaData(
                                fecha = dateTimeState.selectedDate,
                                detalle = "Oficina $selectedCity\n${dateTimeState.selectedTimeInicio} - ${dateTimeState.selectedTimeFin}",
                                iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png"
                            )
                        )
                        // Navegamos hacia atrás (volvemos a la pantalla de inicio)
                        onBack()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(45.dp)
            ) {
                Text(
                    "Confirmar Reserva",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(10.dp))

            // Plano visual de la oficina (decorativo)
            PlanoSalas()
        }
    }
    // Usamos el componente compartido para los diálogos
    DateTimeSelectionDialogs(state = dateTimeState)
}


@Composable
fun PlanoSalas() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(370.dp)
            .padding(22.dp)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.oficina),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )
    }
}