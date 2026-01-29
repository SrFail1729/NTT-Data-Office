package com.example.nttdata.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class DateTimeSelectionState(
    val datePickerState: DatePickerState,
    val timePickerStateInicio: TimePickerState,
    val timePickerStateFin: TimePickerState
) {
    var showDatePicker by mutableStateOf(false)
    var showTimePickerInicio by mutableStateOf(false)
    var showTimePickerFin by mutableStateOf(false)

    val selectedDate: String
        get() = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: "Selecciona fecha"

    val selectedTimeInicio: String
        get() = String.format("%02d:%02d", timePickerStateInicio.hour, timePickerStateInicio.minute)

    val selectedTimeFin: String
        get() = String.format("%02d:%02d", timePickerStateFin.hour, timePickerStateFin.minute)
    
    val dateMillis: Long?
        get() = datePickerState.selectedDateMillis
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
    state: DateTimeSelectionState
) {
    // 1. Selector de Fecha
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Fecha:", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)

        // Botón que abre el diálogo de fecha
        OutlinedButton(
            onClick = { state.showDatePicker = true },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color(0xFF070F26))
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = Color(0xFF070F26)
            )
            Spacer(Modifier.width(8.dp))
            Text(state.selectedDate, color = Color(0xFF070F26))
        }
    }

    Spacer(Modifier.height(10.dp))

    // 2. Selector de Hora Inicio y Fin
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Hora:",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        // Botón que abre el diálogo de hora de inicio
        OutlinedButton(
            onClick = { state.showTimePickerInicio = true },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color(0xFF070F26))
        ) {
            Icon(
                Icons.Default.Schedule,
                contentDescription = null,
                tint = Color(0xFF070F26)
            )
            Spacer(Modifier.width(8.dp))
            Text(state.selectedTimeInicio, color = Color(0xFF070F26))
        }

        // Botón que abre el diálogo de hora de fin
        OutlinedButton(
            onClick = { state.showTimePickerFin = true },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color(0xFF070F26))
        ) {
            Icon(
                Icons.Default.Schedule,
                contentDescription = null,
                tint = Color(0xFF070F26)
            )
            Spacer(Modifier.width(8.dp))
            Text(state.selectedTimeFin, color = Color(0xFF070F26))
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
            onDismissRequest = { state.showTimePickerInicio = false },
            confirmButton = {
                TextButton(onClick = { state.showTimePickerInicio = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { state.showTimePickerInicio = false }) {
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
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(328.dp) // Ancho estándar
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
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

// Función de utilidad para convertir milisegundos a una fecha legible (ej: "25 Nov, Lunes")
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM, EEEE", Locale("es", "ES"))
    return formatter.format(Date(millis))
}
