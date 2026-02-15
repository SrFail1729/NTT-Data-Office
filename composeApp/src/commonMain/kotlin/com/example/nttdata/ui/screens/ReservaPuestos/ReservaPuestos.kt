package com.example.nttdata.ui.screens.ReservaPuestos

import com.example.nttdata.isWebPlatform
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nttdata.ui.screens.CitasViewModel
import com.example.nttdata.ui.screens.OficinaSelection.OficinaViewModel
import com.example.nttdata.ui.screens.OficinaSelection.OficinaSelector
import com.example.nttdata.ui.components.OfficeMapRenderer
import com.example.nttdata.ui.components.HeaderReserva
import com.example.nttdata.ui.components.BarraInferiorComun
import com.example.nttdata.ui.components.rememberDateTimeSelectionState
import com.example.nttdata.ui.components.DateTimeSelectors
import com.example.nttdata.ui.components.DateTimeSelectionDialogs
import com.example.nttdata.domain.model.CitaData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaPuestos(
    viewModel: CitasViewModel,
    oficinaViewModel: OficinaViewModel = viewModel(), // Inyectamos o creamos el VM
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    // Obtenemos la lista de nombres de oficina disponibles
    val availableOffices = oficinaViewModel.oficinas.map { it.name }

    // Estado para la oficina seleccionada. Por defecto la primera o vacía.
    var selectedOfficeName by remember { mutableStateOf(if (availableOffices.isNotEmpty()) availableOffices.first() else "") }

    // Obtenemos el objeto Office real seleccionado
    val currentOffice = oficinaViewModel.oficinas.find { it.name == selectedOfficeName }

    // Estado para la silla seleccionada
    var selectedChairId by remember { mutableStateOf<String?>(null) }

    val dateTimeState = rememberDateTimeSelectionState()

    Scaffold(
        topBar = {
            HeaderReserva(onBack)
        },
        bottomBar = {
            BarraInferiorComun(
                onMenuClick = onMenuClick,
                onBack = onBack
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Lógica Responsiva
            val screenWidth = maxWidth
            val isSmallScreen = screenWidth < 360.dp

            // Configuraciones dinámicas
            val sidePadding = if (isSmallScreen) 12.dp else 16.dp
            val labelWidth = if (isSmallScreen) 65.dp else 85.dp
            val labelFontSize =
                if (isSmallScreen) 14.sp else 16.sp // Reduje un poco para asegurar "mínimo espacio"
            val boxHeight = if (isSmallScreen) 48.dp else 56.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = sidePadding, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = if (isWebPlatform()) {
                        Modifier.widthIn(max = 400.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                ) {
                    OficinaSelector(
                        selectedCity = selectedOfficeName,
                        availableOffices = availableOffices,
                        onCitySelected = {
                            selectedOfficeName = it
                            selectedChairId = null
                        }
                    )

                    Spacer(Modifier.height(12.dp))

                    DateTimeSelectors(
                        state = dateTimeState
                    )
                }

                Spacer(Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentOffice != null) {
                        Text(
                            text = "Selecciona tu puesto",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = if (isSmallScreen) 16.sp else 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Box(
                            modifier = if (isWebPlatform()) {
                                Modifier.weight(1f).widthIn(max = 1000.dp)
                            } else {
                                Modifier.weight(1f).fillMaxWidth()
                            }
                        ) {
                            OfficeMapRenderer(
                                office = currentOffice,
                                selectedChairId = selectedChairId,
                                onChairSelected = { selectedChairId = it }
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .then(
                                    if (isWebPlatform()) {
                                        Modifier.widthIn(max = 1000.dp)
                                    } else {
                                        Modifier.fillMaxWidth()
                                    }
                                )
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                "Selecciona una oficina válida para ver el mapa",
                                fontSize = labelFontSize
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                // BOTÓN DE CONFIRMACIÓN
                val isEnabled = dateTimeState.dateMillis != null && selectedChairId != null
                Button(
                    onClick = {
                        if (isEnabled) {
                            viewModel.anadirCita(
                                CitaData(
                                    fecha = dateTimeState.selectedDate,
                                    detalle = "Oficina $selectedOfficeName\nPuesto: $selectedChairId\nHorario: ${dateTimeState.selectedTimeInicio} - ${dateTimeState.selectedTimeFin}",
                                    iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png",
                                    targetQr = selectedChairId!!
                                )
                            )
                            onBack()
                        }
                    },
                    enabled = isEnabled,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF070F26),
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .then(
                            if (isWebPlatform()) {
                                Modifier.widthIn(max = 400.dp)
                            } else {
                                Modifier.fillMaxWidth()
                            }
                        )
                        .height(56.dp)
                ) {
                    Text(
                        "Confirmar Reserva",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        // Usamos el componente compartido para los diálogos
        DateTimeSelectionDialogs(state = dateTimeState)
    }
}

@Preview
@Composable
fun ReservaPuestosPreview() {
    com.example.nttdata.ui.theme.NttDataTheme {
        ReservaPuestos(
            viewModel = CitasViewModel()
        )
    }
}