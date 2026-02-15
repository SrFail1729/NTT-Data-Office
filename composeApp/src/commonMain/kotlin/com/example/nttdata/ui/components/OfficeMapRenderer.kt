package com.example.nttdata.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nttdata.domain.model.ChairItem
import com.example.nttdata.domain.model.DoorItem
import com.example.nttdata.domain.model.Office
import com.example.nttdata.domain.model.PlantItem
import com.example.nttdata.domain.model.TableItem
import com.example.nttdata.domain.model.WallItem

@Composable
fun OfficeMapRenderer(
    office: Office,
    selectedChairId: String? = null,
    onChairSelected: (String) -> Unit = {},
    isExpandedView: Boolean = false,
    onCloseRequest: () -> Unit = {}
) {
    // Estado de transformación
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var showExpanded by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer()

    val minScale = 1f
    val maxScale = 5f

    // Gestor de transformaciones (Zoom y Pan)
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        val newScale = (scale * zoomChange).coerceIn(minScale, maxScale)
        // Ajustamos el offset para que escale desde el centro o siga el dedo (simplificado: acumular offset)
        // Limitamos el pan para no perder el mapa.
        val newOffset = offset + (offsetChange * scale) // Ajuste sensitivo con el zoom
        
        scale = newScale
        offset = newOffset 
    }

    // Contenedor principal con constraints para calcular escalas iniciales
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clipToBounds()
    ) {
        val containerWidth = constraints.maxWidth.toFloat()
        val containerHeight = constraints.maxHeight.toFloat()
        
        // Dimensiones del contenido (Oficina)
        val contentWidth = office.customWidth
        val contentHeight = office.customHeight
        
        // Calcular escala inicial para ajustar ("Fit Center")
        val initialScale = minOf(
            containerWidth / contentWidth,
            containerHeight / contentHeight
        ) * 0.9f // Un poco de margen

        // Centro del contenedor
        val centerX = containerWidth / 2f
        val centerY = containerHeight / 2f

        // Lógica de Canvas y Detección de Toques
        Box(
            modifier = Modifier
                .fillMaxSize()
                .transformable(state = state) // Gestos de Zoom/Pan
                .pointerInput(initialScale, scale, offset, centerX, centerY, contentWidth, contentHeight) {
                    detectTapGestures { tapOffset ->
                        // LÓGICA DE HIT TESTING INVERSA
                        // 1. Convertir coordenadas de pantalla (tapOffset) a coordenadas locales de la oficina
                        
                        val totalScale = initialScale * scale
                        
                        val localX = ((tapOffset.x - centerX - offset.x) / totalScale) + (contentWidth / 2f)
                        val localY = ((tapOffset.y - centerY - offset.y) / totalScale) + (contentHeight / 2f)

                        // 2. Comprobar colisión con sillas
                        val touchRadius = 30f // Área de toque generosa en coordenadas locales
                        
                        val tappedChair = office.items.filterIsInstance<ChairItem>().find { item ->
                            val itemRadius = 25f
                            // Asumimos que x,y es la esquina superior izquierda
                            val itemCenterX = item.x + itemRadius
                            val itemCenterY = item.y + itemRadius
                            
                            val dx = localX - itemCenterX
                            val dy = localY - itemCenterY
                            
                            (dx * dx + dy * dy) <= ((itemRadius + touchRadius) * (itemRadius + touchRadius))
                        }

                        if (tappedChair != null) {
                            onChairSelected(tappedChair.number)
                        }
                    }
                }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Aplicar transformaciones visuales
                        val totalScale = initialScale * scale
                        scaleX = totalScale
                        scaleY = totalScale
                        translationX = offset.x
                        translationY = offset.y
                        // El origen de la escala por defecto es el centro del layout (Box), que coincide con containerCenter
                    }
            ) {
                // Dibujar contenido centrado en el origen (0,0) del Canvas relativo a la transformación
                // Ya que usamos graphicsLayer centrado, dibujamos el contenido centrado en el Canvas.
                    
                translate(left = -contentWidth / 2f + size.width / 2f, top = -contentHeight / 2f + size.height / 2f) {
                    
                    // 1. Fondo de la oficina (Suelo)
                    drawRoundRect(
                        color = Color.White,
                        topLeft = Offset(0f, 0f),
                        size = Size(contentWidth, contentHeight),
                        cornerRadius = CornerRadius(20f),
                        style = Fill
                    )
                    drawRoundRect(
                        color = Color(0xFFDDDDDD),
                        topLeft = Offset(0f, 0f),
                        size = Size(contentWidth, contentHeight),
                        cornerRadius = CornerRadius(20f),
                        style = Stroke(width = 4f)
                    )

                    // 2. Elementos
                    office.items.forEach { item ->
                        when (item) {
                            is TableItem -> {
                                drawRoundRect(
                                    color = Color(0xFFE0E0E0),
                                    topLeft = Offset(item.x, item.y),
                                    size = Size(item.width, item.height),
                                    cornerRadius = CornerRadius(8f)
                                )
                                drawRoundRect(
                                    color = Color(0xFFBDBDBD),
                                    topLeft = Offset(item.x, item.y),
                                    size = Size(item.width, item.height),
                                    cornerRadius = CornerRadius(8f),
                                    style = Stroke(width = 2f)
                                )
                            }
                            is ChairItem -> {
                                val radius = 25f
                                val isSelected = item.number == selectedChairId
                                
                                // Coordenadas del centro de la silla (x,y es top-left)
                                val centerX = item.x + radius
                                val centerY = item.y + radius

                                // Color según estado
                                val chairColor = if (isSelected) Color(0xFF4CAF50) else Color(0xFF2196F3)
                                val strokeColor = if (isSelected) Color(0xFF1B5E20) else Color(0xFF0D47A1)

                                drawCircle(
                                    color = chairColor,
                                    radius = radius,
                                    center = Offset(centerX, centerY)
                                )
                                // Borde
                                drawCircle(
                                    color = strokeColor,
                                    radius = radius,
                                    center = Offset(centerX, centerY),
                                    style = Stroke(width = if (isSelected) 4f else 2f)
                                )

                                // Texto
                                val textLayoutResult = textMeasurer.measure(
                                    text = item.number,
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                )
                                
                                val textSize = textLayoutResult.size
                                drawText(
                                    textLayoutResult = textLayoutResult,
                                    topLeft = Offset(
                                        centerX - textSize.width / 2,
                                        centerY - textSize.height / 2
                                    )
                                )
                            }
                            is PlantItem -> {
                                val centerX = item.x + item.radius
                                val centerY = item.y + item.radius
                                // Círculo principal verde
                                drawCircle(
                                    color = Color(0xFF4CAF50),
                                    radius = item.radius,
                                    center = Offset(centerX, centerY)
                                )
                                // Borde verde oscuro
                                drawCircle(
                                    color = Color(0xFF2E7D32),
                                    radius = item.radius,
                                    center = Offset(centerX, centerY),
                                    style = Stroke(width = 3f)
                                )
                                // Pequeños círculos decorativos (hojas)
                                val leafRadius = item.radius * 0.2f
                                drawCircle(
                                    color = Color(0xFF66BB6A),
                                    radius = leafRadius,
                                    center = Offset(centerX - item.radius * 0.4f, centerY - item.radius * 0.3f)
                                )
                                drawCircle(
                                    color = Color(0xFF66BB6A),
                                    radius = leafRadius,
                                    center = Offset(centerX + item.radius * 0.4f, centerY - item.radius * 0.3f)
                                )
                                drawCircle(
                                    color = Color(0xFF66BB6A),
                                    radius = leafRadius,
                                    center = Offset(centerX, centerY + item.radius * 0.3f)
                                )
                            }
                            is DoorItem -> {
                                // Dibujar Puerta
                                drawRect(
                                    color = Color(0xFF8B4513),
                                    topLeft = Offset(item.x, item.y),
                                    size = Size(item.width, item.height)
                                )
                                drawLine(
                                    color = Color.White,
                                    start = Offset(item.x, item.y),
                                    end = Offset(item.x + item.width, item.y + item.height),
                                    strokeWidth = 2f
                                )
                            }
                            is WallItem -> {
                                // Dibujar Pared
                                drawRect(
                                    color = Color.DarkGray,
                                    topLeft = Offset(item.x, item.y),
                                    size = Size(item.width, item.height)
                                )
                                val step = 10f
                                for (i in 0..(item.width / step).toInt()) {
                                    drawLine(
                                        Color.Gray,
                                        Offset(item.x + i * step, item.y),
                                        Offset(item.x + i * step + 5f, item.y + item.height),
                                        strokeWidth = 1f
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Controles Flotantes ---
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .zIndex(1f) // Asegurar que estén por encima
        ) {
            val iconBtn = if (!isExpandedView) Icons.Default.Fullscreen else Icons.Default.Close
            val onClickAction = if (!isExpandedView) { { showExpanded = true } } else onCloseRequest

            FloatingControlButton(
                icon = iconBtn,
                onClick = onClickAction,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            // Controles de Zoom 
            Column(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                 FloatingControlButton(
                    icon = Icons.Default.Add,
                    onClick = { scale = (scale + 0.5f).coerceIn(minScale, maxScale) }
                )
                 FloatingControlButton(
                    icon = Icons.Default.Remove,
                    onClick = { scale = (scale - 0.5f).coerceIn(minScale, maxScale) }
                )
            }
        }
    }

    // Modal Expandido
    if (showExpanded) {
        Dialog(
            onDismissRequest = { showExpanded = false },
            properties = DialogProperties(usePlatformDefaultWidth = false) // Full screen
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = 0.8f) // Fondo oscuro para inmersión
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                     OfficeMapRenderer(
                        office = office,
                        selectedChairId = selectedChairId,
                        onChairSelected = {
                            onChairSelected(it)
                            showExpanded = false 
                        },
                        isExpandedView = true,
                        onCloseRequest = { showExpanded = false }
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingControlButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .size(40.dp)
            .background(Color.White.copy(alpha = 0.9f), CircleShape)
            .border(1.dp, Color.LightGray, CircleShape),
        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
    }
}
