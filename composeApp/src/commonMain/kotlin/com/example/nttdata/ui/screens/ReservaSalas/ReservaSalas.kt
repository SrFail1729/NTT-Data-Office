package com.example.nttdata.ui.screens.ReservaSalas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun ReservaSalas(
    viewModel: CitasViewModel,
    oficinaViewModel: OficinaViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    val availableOffices = oficinaViewModel.oficinas.map { it.name }
    var selectedOfficeName by remember { mutableStateOf(if (availableOffices.isNotEmpty()) availableOffices.first() else "") }
    val currentOffice = oficinaViewModel.oficinas.find { it.name == selectedOfficeName }
    var selectedChairId by remember { mutableStateOf<String?>(null) }

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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Responsiveness Logic (Same as ReservaPuestos)
            val screenWidth = maxWidth
            val isSmallScreen = screenWidth < 360.dp
            
            val sidePadding = if (isSmallScreen) 12.dp else 16.dp
            val labelWidth = if (isSmallScreen) 65.dp else 85.dp
            val labelFontSize = if (isSmallScreen) 14.sp else 16.sp 
            val boxHeight = if (isSmallScreen) 48.dp else 56.dp
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = sidePadding)
            ) {

                Spacer(Modifier.height(10.dp))
    
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
    
                Spacer(Modifier.height(16.dp))
    
                if (currentOffice != null) {
                    Box(modifier = Modifier.weight(1f)) {
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
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                             .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text("Selecciona una sala válida")
                    }
                }
                
                Spacer(Modifier.height(10.dp))
    
                Button(
                    onClick = {
                        if (dateTimeState.dateMillis != null && selectedChairId != null) {
                            viewModel.anadirCita(
                                CitaData(
                                    fecha = dateTimeState.selectedDate,
                                    detalle = "Sala: $selectedOfficeName\nPuesto: $selectedChairId\nRunning Time: ${dateTimeState.selectedTimeInicio} - ${dateTimeState.selectedTimeFin}",
                                    iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png",
                                    targetQr = selectedChairId!!
                                )
                            )
                            onBack()
                        }
                    },
                    enabled = dateTimeState.dateMillis != null && selectedChairId != null,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF070F26),
                         disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(bottom = 16.dp)
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
    }
    // Usamos el componente compartido para los diálogos
    DateTimeSelectionDialogs(state = dateTimeState)
}

@Preview
@Composable
fun ReservaSalasPreview() {
    com.example.nttdata.ui.theme.NttDataTheme {
        ReservaSalas(
            viewModel = CitasViewModel()
        )
    }
}
