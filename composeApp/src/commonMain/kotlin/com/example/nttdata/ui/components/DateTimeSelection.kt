package com.example.nttdata.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import com.example.nttdata.isWebPlatform
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
class DateTimeSelectionState(
    val datePickerState: DatePickerState,
    val timePickerStateInicio: TimePickerState,
    val timePickerStateFin: TimePickerState
) {
    var showDatePicker by mutableStateOf(false)
    var showTimePickerInicio by mutableStateOf(false)
    var showTimePickerFin by mutableStateOf(false)
    var shouldOpenFinAfterInicio by mutableStateOf(false)

    val selectedDate: String
        get() = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: "Selec. fecha"

    val selectedTimeInicio: String
        get() = "${timePickerStateInicio.hour.toString().padStart(2, '0')}:${timePickerStateInicio.minute.toString().padStart(2, '0')}"

    val selectedTimeFin: String
        get() = "${timePickerStateFin.hour.toString().padStart(2, '0')}:${timePickerStateFin.minute.toString().padStart(2, '0')}"
    
    val dateMillis: Long?
        get() = datePickerState.selectedDateMillis
    
    fun openTimeRangePicker() {
        shouldOpenFinAfterInicio = true
        showTimePickerInicio = true
    }
    
    fun onTimeInicioConfirmed() {
        showTimePickerInicio = false
        if (shouldOpenFinAfterInicio) {
            showTimePickerFin = true
            shouldOpenFinAfterInicio = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDateTimeSelectionState(): DateTimeSelectionState {
    val datePickerState = rememberDatePickerState()
    val timePickerStateInicio = rememberTimePickerState()
    val timePickerStateFin = rememberTimePickerState()

    return remember(datePickerState, timePickerStateInicio, timePickerStateFin) {
        DateTimeSelectionState(datePickerState, timePickerStateInicio, timePickerStateFin)
    }
}

@Composable
fun DateTimeSelectors(
    state: DateTimeSelectionState,
    labelWidth: Dp = 0.dp,
    labelFontSize: TextUnit = 14.sp,
    boxHeight: Dp = 56.dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        // Selector de Fecha
        Card(
            modifier = Modifier
                .weight(1f)
                .height(boxHeight)
                .clickable { state.showDatePicker = true },
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Fecha",
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(Modifier.width(8.dp))
                
                Text(
                    text = state.selectedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

        // Selector de Hora (Rango)
        Card(
            modifier = Modifier
                .weight(1f)
                .height(boxHeight)
                .clickable { state.openTimeRangePicker() },
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Icon(
                    Icons.Default.Schedule,
                    contentDescription = "Hora",
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(Modifier.width(8.dp))
                
                Text(
                    text = "${state.selectedTimeInicio} - ${state.selectedTimeFin}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelectionDialogs(
    state: DateTimeSelectionState
) {
    // Diálogo de Selección de Fecha
    if (state.showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { state.showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { state.showDatePicker = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { state.showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = state.datePickerState)
        }
    }

    // Diálogo de Selección de Hora Inicio
    if (state.showTimePickerInicio) {
        TimePickerDialog(
            onDismissRequest = { 
                state.showTimePickerInicio = false
                state.shouldOpenFinAfterInicio = false
            },
            confirmButton = {
                TextButton(onClick = { state.onTimeInicioConfirmed() }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    state.showTimePickerInicio = false
                    state.shouldOpenFinAfterInicio = false
                }) {
                    Text("Cancelar")
                }
            }
        ) {
            TimePicker(state = state.timePickerStateInicio)
        }
    }

    // Diálogo de Selección de Hora Fin
    if (state.showTimePickerFin) {
        TimePickerDialog(
            onDismissRequest = { state.showTimePickerFin = false },
            confirmButton = {
                TextButton(onClick = { state.showTimePickerFin = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { state.showTimePickerFin = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            TimePicker(state = state.timePickerStateFin)
        }
    }
}

// Función auxiliar para mostrar el diálogo del TimePicker con el estilo correcto
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismissRequest,
        properties = androidx.compose.ui.window.DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        androidx.compose.material3.Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(if (isWebPlatform()) 580.dp else 328.dp) // Mayor ancho para modo landscape en web
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(if (isWebPlatform()) 32.dp else 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton()
                    confirmButton()
                }
            }
        }
    }
}

// Función de utilidad para convertir milisegundos a una fecha legible (ej: "15 Nov, 2024")
fun convertMillisToDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    val monthNames = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
    return "${date.dayOfMonth} ${monthNames[date.monthNumber - 1]}, ${date.year}"
}
