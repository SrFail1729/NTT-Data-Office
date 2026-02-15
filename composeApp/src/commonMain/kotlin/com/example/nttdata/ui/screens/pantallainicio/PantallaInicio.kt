package com.example.nttdata.ui.screens.pantallainicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nttdata.domain.model.CitaData
import com.example.nttdata.ui.components.BarraInferiorComun
import com.example.nttdata.ui.components.CoilImageWrapper
import com.example.nttdata.ui.screens.CitasViewModel
import com.example.nttdata.ui.components.CameraPermissionEffect
import com.example.nttdata.ui.components.QrScanner
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.reservar_puesto
import nttdata.composeapp.generated.resources.reservar_sala
import com.example.nttdata.isWebPlatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PantallaInicio(
    viewModel: CitasViewModel,
    modifier: Modifier = Modifier,
    onReservaSalaClick: () -> Unit = {},
    onReservaPuestoClick: () -> Unit = {},
    onReservaClick: (Int) -> Unit = {},
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    // Estado local para controlar si tenemos permiso antes de mostrar la cámara
    var hasCameraPermission by remember { mutableStateOf(false) }

    // Gestionamos el permiso de cámara de forma multiplataforma
    CameraPermissionEffect(
        shouldAskPermission = viewModel.showQrScanner,
        onPermissionResult = { isGranted ->
            hasCameraPermission = isGranted
            if (!isGranted) {
                // Si el usuario deniega el permiso, cancelamos para no mostrar el cuadro negro
                viewModel.cancelarValidacion()
            }
        }
    )

    Scaffold(
        topBar = {
            HeaderUsuario(onBack)
        },
        bottomBar = {
            BarraInferiorComun(
                onMenuClick = onMenuClick,
                onBack = onBack
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {

                Spacer(Modifier.height(24.dp))

                // Usamos LazyColumn para que la lista sea eficiente y scrolleable
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(viewModel.citas) { index, cita ->
                        Box(
                            modifier = if (isWebPlatform()) {
                                Modifier.widthIn(max = 600.dp).fillMaxWidth()
                            } else {
                                Modifier.fillMaxWidth()
                            }
                        ) {
                            CitaItem(
                                cita = cita,
                                onDelete = { viewModel.eliminarCita(it) },
                                onConfirm = { viewModel.iniciarValidacion(it) },
                                onClick = { onReservaClick(index) }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(40.dp))

                // Componente que contiene las imágenes para navegar a reservar
                GaleriaImagenes(
                    onReservaPuestoClick = onReservaPuestoClick,
                    onReservaSalaClick = onReservaSalaClick
                )

                Spacer(Modifier.height(24.dp))
            }

            // Overlay del escáner QR
            if (viewModel.showQrScanner && hasCameraPermission) {
                Dialog(onDismissRequest = { viewModel.cancelarValidacion() }) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Black)
                    ) {
                        QrScanner(
                            modifier = Modifier.fillMaxSize(),
                            onQrDetected = { qrContent ->
                                viewModel.validarQr(qrContent)
                            }
                        )
                        androidx.compose.material3.IconButton(
                            onClick = { viewModel.cancelarValidacion() },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Cerrar",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HeaderUsuario(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF070F26))
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón de retroceso estándar
        androidx.compose.material3.IconButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        // Imagen de perfil del usuario
        CoilImageWrapper(
            imageUrl = "https://cdn.pixabay.com/photo/2023/02/18/11/00/icon-7797704_1280.png",
            modifier = Modifier
                .size(78.dp)
                .padding(start = 2.dp, end = 7.dp, top = 5.dp, bottom = 5.dp)
                .clip(CircleShape) // Recorte circular
        )
        // Nombre del usuario
        Text(
            "Usuario de prueba",
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
        )
    }
}


@Composable
fun CitaItem(
    cita: CitaData,
    onDelete: (CitaData) -> Unit,
    onConfirm: (CitaData) -> Unit,
    onClick: () -> Unit
) {
    // Tarjeta visual para cada cita
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(21.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFD9D9D9)) // Fondo gris claro
            .clickable { onClick() }
            .padding(9.dp)
    ) {
        // Fila superior: Fecha e icono
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

        // Fila inferior: Detalle y botones
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(cita.detalle, color = Color(0xFF070F26))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón para cancelar (elimina la cita)
                OutlinedButton(
                    onClick = { onDelete(cita) },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF070F26)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(38.dp)
                ) {
                    Text(
                        "Cancelar",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(50.dp))

                if (!cita.isConfirmed) {
                    // Botón para confirmar cita por QR
                    OutlinedButton(
                        onClick = { onConfirm(cita) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(
                                0xFF0073BD
                            )
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(
                            "Confirmar Cita",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Text(
                        "Confirmada ✅",
                        color = Color(0xFF006400),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GaleriaImagenes(onReservaPuestoClick: () -> Unit, onReservaSalaClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = if (isWebPlatform()) {
            Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
        } else {
            Arrangement.SpaceEvenly
        }
    ) {
        // Tarjeta interactiva para "Reservar Puesto"
        androidx.compose.material3.Card(
            onClick = onReservaPuestoClick,
            shape = RoundedCornerShape(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.reservar_puesto),
                contentDescription = "Reservar Puesto",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )
        }

        // Tarjeta interactiva para "Reservar Sala"
        androidx.compose.material3.Card(
            onClick = onReservaSalaClick,
            shape = RoundedCornerShape(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.reservar_sala),
                contentDescription = "Reservar Sala",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
fun PantallaInicioPreview() {
    com.example.nttdata.ui.theme.NttDataTheme {
        PantallaInicio(
            viewModel = CitasViewModel()
        )
    }
}
