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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaPuestos7(
    citas: SnapshotStateList<CitaData>, // Lista mutable de citas compartida
    onBack: () -> Unit = {} // Callback para navegar hacia atrás
) {
    // ----------------------------------------------------------------
    // ESTADOS PARA LOS SELECTORES (Date Picker y Time Pickers)
    // ----------------------------------------------------------------

    // Estado para controlar la visibilidad del diálogo de selección de FECHA
    var showDatePicker by remember { mutableStateOf(false) }
    // Estado interno del DatePicker de Material3
    val datePickerState = rememberDatePickerState()

    // Lógica para obtener la fecha seleccionada formateada.
    // Si no hay fecha seleccionada (null), mostramos un texto por defecto.
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: "Selecciona fecha"

    // Estado para controlar la visibilidad del diálogo de selección de HORA INICIO
    var showTimePickerInicio by remember { mutableStateOf(false) }
    // Estado interno del TimePicker para la hora de inicio
    val timePickerStateInicio = rememberTimePickerState()
    // Formateamos la hora seleccionada a String (HH:mm)
    val selectedTimeInicio =
        String.format("%02d:%02d", timePickerStateInicio.hour, timePickerStateInicio.minute)

    // Estado para controlar la visibilidad del diálogo de selección de HORA FIN
    var showTimePickerFin by remember { mutableStateOf(false) }
    // Estado interno del TimePicker para la hora de fin
    val timePickerStateFin = rememberTimePickerState()
    // Formateamos la hora seleccionada a String (HH:mm)
    val selectedTimeFin =
        String.format("%02d:%02d", timePickerStateFin.hour, timePickerStateFin.minute)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .background(Color.White)
    ) {

        // Cabecera con botón de atrás y título
        HeaderReserva(onBack)

        Spacer(Modifier.height(16.dp))

        // Componente visual para mostrar la oficina seleccionada (fijo por ahora)
        OficinaSelector(
            "Valencia",
            "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/vjlbfl27_expires_30_days.png"
        )

        Spacer(Modifier.height(24.dp))

        // ----------------------------------------------------------------
        // SECCIÓN DE BOTONES SELECTORES
        // ----------------------------------------------------------------

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
                onClick = { showDatePicker = true }, // Al hacer clic, mostramos el diálogo
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFF070F26))
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color(0xFF070F26))
                Spacer(Modifier.width(8.dp))
                Text(selectedDate, color = Color(0xFF070F26))
            }
        }

        Spacer(Modifier.height(16.dp))

        // 2. Selector de Hora Inicio
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Hora Inicio:",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            // Botón que abre el diálogo de hora de inicio
            OutlinedButton(
                onClick = { showTimePickerInicio = true },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFF070F26))
            ) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF070F26))
                Spacer(Modifier.width(8.dp))
                Text(selectedTimeInicio, color = Color(0xFF070F26))
            }
        }

        Spacer(Modifier.height(16.dp))

        // 3. Selector de Hora Fin
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Hora Fin:", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)

            // Botón que abre el diálogo de hora de fin
            OutlinedButton(
                onClick = { showTimePickerFin = true },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFF070F26))
            ) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF070F26))
                Spacer(Modifier.width(8.dp))
                Text(selectedTimeFin, color = Color(0xFF070F26))
            }
        }

        Spacer(Modifier.height(24.dp))

        // ----------------------------------------------------------------
        // BOTÓN DE CONFIRMACIÓN
        // ----------------------------------------------------------------
        Button(
            onClick = {
                // Validamos que se haya seleccionado una fecha antes de guardar
                if (datePickerState.selectedDateMillis != null) {
                    // Añadimos la nueva cita a la lista compartida
                    citas.add(
                        CitaData(
                            fecha = selectedDate,
                            detalle = "Oficina Valencia\n$selectedTimeInicio - $selectedTimeFin",
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
                .height(50.dp)
        ) {
            Text(
                "Confirmar Reserva",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        // Plano visual de la oficina (decorativo)
        PlanoOficina()

        Spacer(Modifier.height(24.dp))
        BarraInferiorReserva()
    }

    // ----------------------------------------------------------------
    // DIÁLOGOS MODALES (Se muestran sobre la UI cuando su estado es true)
    // ----------------------------------------------------------------

    // Diálogo de Selección de Fecha
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false }, // Cierra al tocar fuera
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Diálogo de Selección de Hora Inicio
    if (showTimePickerInicio) {
        TimePickerDialog(
            onDismissRequest = { showTimePickerInicio = false },
            confirmButton = {
                TextButton(onClick = { showTimePickerInicio = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerInicio = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            TimePicker(state = timePickerStateInicio)
        }
    }

    // Diálogo de Selección de Hora Fin
    if (showTimePickerFin) {
        TimePickerDialog(
            onDismissRequest = { showTimePickerFin = false },
            confirmButton = {
                TextButton(onClick = { showTimePickerFin = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerFin = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            TimePicker(state = timePickerStateFin)
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
    // 🔵 Creamos una lista de ejemplo para el preview
    val citasDemo = androidx.compose.runtime.snapshots.SnapshotStateList<CitaData>().apply {
        add(
            CitaData(
                fecha = "25 Noviembre, Martes",
                detalle = "Oficina Valencia\n18:00 - 18:30",
                iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/XBgefxxgLz/06qx6vzm_expires_30_days.png"
            )
        )
    }

    ReservaPuestos7(
        citas = citasDemo,
        onBack = {}
    )
}