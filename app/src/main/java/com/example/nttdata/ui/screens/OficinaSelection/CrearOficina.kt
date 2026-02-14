package com.example.nttdata.ui.screens.OficinaSelection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.nativeCanvas
import com.example.nttdata.domain.model.ChairItem
import com.example.nttdata.domain.model.DoorItem
import com.example.nttdata.domain.model.Office
import com.example.nttdata.domain.model.OfficeItem
import com.example.nttdata.domain.model.PlantItem
import com.example.nttdata.domain.model.TableItem
import com.example.nttdata.domain.model.WallItem
import com.example.nttdata.ui.components.HeaderReserva
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clipToBounds
import kotlin.math.roundToInt

enum class CreationMode {
    LIENZO, ELEMENTO
}

enum class ElementType {
    MESA, SILLA, PLANTA, PUERTA, PARED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearOficina(
    viewModel: OficinaViewModel,
    onBack: () -> Unit
) {
    var officeName by remember { mutableStateOf("") }
    
    // Lista local de items para edición
    val items = remember { mutableStateListOf<OfficeItem>() }
    var nextItemId by remember { mutableStateOf(0) }
    var selectedItemId by remember { mutableStateOf<Int?>(null) }
    var chairCounter by remember { mutableStateOf(1) }

    // Estado para el modo de creación y medidas unificadas
    var creationMode by remember { mutableStateOf(CreationMode.LIENZO) }
    var currentElementType by remember { mutableStateOf(ElementType.MESA) }
    var showElementMenu by remember { mutableStateOf(false) }
    var anchoInput by remember { mutableStateOf("800") }
    var altoInput by remember { mutableStateOf("400") }
    
    // Medidas reales del lienzo (persistentes)
    var officeWidth by remember { mutableStateOf(800f) }
    var officeHeight by remember { mutableStateOf(400f) }
    
    // Indica si estamos editando las medidas del lienzo o de un item
    var isConfirmingMedidas by remember { mutableStateOf(true) }
    
    // Estados para navegación del mapa (zoom/pan)
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformState = rememberTransformableState { _, offsetChange, _ ->
        // Bloqueamos zoom con dedos, solo permitimos navegación (offset)
        offset += offsetChange
    }

    Scaffold(
        topBar = {
            HeaderReserva(onBack, title = "Crear Oficina")
        },
        bottomBar = {
             Button(
                onClick = {
                    if (officeName.isNotEmpty()) {
                        val currentW = if (creationMode == CreationMode.LIENZO) anchoInput.toFloatOrNull() ?: 800f else 800f
                        val currentH = if (creationMode == CreationMode.LIENZO) altoInput.toFloatOrNull() ?: 400f else 400f
                        
                        val newOffice = Office(
                            name = officeName,
                            items = items.toList(),
                            customWidth = officeWidth,
                            customHeight = officeHeight
                        )
                        viewModel.guardarOficina(newOffice)
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding() // Evita solapamiento con la barra del móvil
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF070F26)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Guardar Oficina", color = Color.White, fontSize = 16.sp)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre de la oficina
            OutlinedTextField(
                value = officeName,
                onValueChange = { officeName = it },
                label = { Text("Nombre de la Oficina") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF070F26),
                    focusedLabelColor = Color(0xFF070F26)
                )
            )

            Spacer(Modifier.height(16.dp))

            // Selector de Elemento (Botones redondos/ovalados como en la imagen)
            Text("Creación elementos(px):", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CreationTypeButton(
                    text = "Lienzo",
                    isSelected = creationMode == CreationMode.LIENZO,
                    onClick = { 
                        creationMode = CreationMode.LIENZO 
                        anchoInput = officeWidth.toInt().toString()
                        altoInput = officeHeight.toInt().toString()
                        isConfirmingMedidas = true
                        selectedItemId = null // Deseleccionamos cualquier item al tocar lienzo
                    }
                )
                
                Box {
                    CreationTypeButton(
                        text = "Elementos +",
                        isSelected = creationMode == CreationMode.ELEMENTO,
                        onClick = { 
                            showElementMenu = !showElementMenu
                        }
                    )
                    
                    androidx.compose.material3.DropdownMenu(
                        expanded = showElementMenu,
                        onDismissRequest = { showElementMenu = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text("Mesa") },
                            onClick = {
                                creationMode = CreationMode.ELEMENTO
                                currentElementType = ElementType.MESA
                                anchoInput = "120"
                                altoInput = "60"
                                items.add(TableItem(nextItemId++, 0f, 0f, width = 120f, height = 60f))
                                selectedItemId = nextItemId - 1
                                isConfirmingMedidas = true
                                showElementMenu = false
                            }
                        )
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text("Silla") },
                            onClick = {
                                creationMode = CreationMode.ELEMENTO
                                currentElementType = ElementType.SILLA
                                anchoInput = "50"
                                altoInput = "50"
                                items.add(ChairItem(nextItemId++, 0f, 0f, number = chairCounter.toString()))
                                chairCounter++
                                selectedItemId = nextItemId - 1
                                isConfirmingMedidas = true
                                showElementMenu = false
                            }
                        )
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text("Planta") },
                            onClick = {
                                creationMode = CreationMode.ELEMENTO
                                currentElementType = ElementType.PLANTA
                                anchoInput = "60"
                                altoInput = "60"
                                items.add(PlantItem(nextItemId++, 0f, 0f, radius = 30f))
                                selectedItemId = nextItemId - 1
                                isConfirmingMedidas = false // Las plantas suelen ser redondas fijas por ahora
                                showElementMenu = false
                            }
                        )
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text("Puerta") },
                            onClick = {
                                creationMode = CreationMode.ELEMENTO
                                currentElementType = ElementType.PUERTA
                                anchoInput = "60"
                                altoInput = "10"
                                items.add(DoorItem(nextItemId++, 0f, 0f, width = 60f, height = 10f))
                                selectedItemId = nextItemId - 1
                                isConfirmingMedidas = true
                                showElementMenu = false
                            }
                        )
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text("Pared") },
                            onClick = {
                                creationMode = CreationMode.ELEMENTO
                                currentElementType = ElementType.PARED
                                anchoInput = "100"
                                altoInput = "10"
                                items.add(WallItem(nextItemId++, 0f, 0f, width = 100f, height = 10f))
                                selectedItemId = nextItemId - 1
                                isConfirmingMedidas = true
                                showElementMenu = false
                            }
                        )
                    }
                }
            }

            // Inputs Unificados de Medidas (Solo si estamos seleccionando un item o el lienzo)
            if (isConfirmingMedidas) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = anchoInput,
                        onValueChange = { if (it.all { c -> c.isDigit() }) anchoInput = it },
                        label = { Text("Ancho") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = altoInput,
                        onValueChange = { if (it.all { c -> c.isDigit() }) altoInput = it },
                        label = { Text("Alto") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    
                    // Botón de Confirmación (Check)
                    IconButton(
                        onClick = {
                            val w = anchoInput.toFloatOrNull() ?: 50f
                            val h = altoInput.toFloatOrNull() ?: 50f
                            
                            if (creationMode == CreationMode.LIENZO) {
                                officeWidth = w
                                officeHeight = h
                            } else {
                                selectedItemId?.let { id ->
                                    val index = items.indexOfFirst { it.id == id }
                                    if (index != -1) {
                                        val item = items[index]
                                        if (item is TableItem) {
                                            items[index] = TableItem(item.id, item.x, item.y, width = w, height = h, item.isVertical)
                                        } else if (item is DoorItem) {
                                            items[index] = DoorItem(item.id, item.x, item.y, width = w, height = h, item.isVertical)
                                        } else if (item is WallItem) {
                                            items[index] = WallItem(item.id, item.x, item.y, width = w, height = h, item.isVertical)
                                        }
                                    }
                                }
                            }
                            isConfirmingMedidas = false
                        },
                        modifier = Modifier.background(Color(0xFF070F26), RoundedCornerShape(8.dp))
                    ) {
                        Icon(androidx.compose.material.icons.Icons.Default.Add, contentDescription = "Confirmar", tint = Color.White)
                    }
                }
            } else if (selectedItemId != null || creationMode == CreationMode.LIENZO) {
                // Botón para volver a editar medidas si algo está seleccionado
                Button(
                    onClick = { isConfirmingMedidas = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Editar Medidas", color = Color.Black)
                }
            }

            Spacer(Modifier.height(16.dp))

            // ÁREA DEL MAPA (CANVAS)
            val gridSize = 20f // Tamaño de la cuadrícula en píxeles (aprox 20dp)
            
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .border(2.dp, Color.LightGray)
                    .background(Color(0xFFEEEEEE))
                    .transformable(state = transformState)
                    .clipToBounds()
            ) {
                val officeW = officeWidth
                val officeH = officeHeight
                
                // Escala de ajuste inicial (fit)
                val canvasW_px = constraints.maxWidth.toFloat()
                val canvasH_px = constraints.maxHeight.toFloat()
                
                val bestScale = (minOf(
                    (canvasW_px * 2.5f) / officeW, 
                    (canvasH_px * 2.5f) / officeH,
                    1.0f
                ) * 0.4f).coerceAtLeast(0.01f)

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale * bestScale,
                            scaleY = scale * bestScale,
                            translationX = offset.x,
                            translationY = offset.y,
                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                        )
                        .pointerInput(anchoInput, altoInput, scale, offset, officeWidth, officeHeight) {
                            detectTapGestures { tapOffset ->
                                val s = scale * bestScale
                                // El graphicsLayer con TransformOrigin(0.5f, 0.5f) escala desde el centro del componente.
                                // Para obtener la coordenada local inversa:
                                // 1. Restamos el desplazamiento (offset)
                                // 2. Centramos respecto al tamaño del Canvas (size.width/2, size.height/2)
                                // 3. Dividimos por la escala
                                // 4. Volvemos a la coordenada original (sumando el centro)
                                val centerX = size.width / 2
                                val centerY = size.height / 2
                                
                                val localX = (tapOffset.x - offset.x - centerX) / s + centerX
                                val localY = (tapOffset.y - offset.y - centerY) / s + centerY
                                
                                val offX = centerX - officeW / 2
                                val offY = centerY - officeH / 2

                                val found = items.findLast { item ->
                                    when (item) {
                                        is TableItem -> {
                                            val absX = item.x + offX
                                            val absY = item.y + offY
                                            localX >= absX && localX <= absX + item.width &&
                                            localY >= absY && localY <= absY + item.height
                                        }
                                        is ChairItem -> {
                                            val radius = 25f
                                            val absX = item.x + offX + radius
                                            val absY = item.y + offY + radius
                                            val dx = localX - absX
                                            val dy = localY - absY
                                            (dx * dx + dy * dy) <= (radius * radius)
                                        }
                                        is PlantItem -> {
                                            val absX = item.x + offX + item.radius
                                            val absY = item.y + offY + item.radius
                                            val dx = localX - absX
                                            val dy = localY - absY
                                            (dx * dx + dy * dy) <= (item.radius * item.radius)
                                        }
                                        is DoorItem -> {
                                            val absX = item.x + offX
                                            val absY = item.y + offY
                                            localX >= absX && localX <= absX + item.width &&
                                            localY >= absY && localY <= absY + item.height
                                        }
                                        is WallItem -> {
                                            val absX = item.x + offX
                                            val absY = item.y + offY
                                            localX >= absX && localX <= absX + item.width &&
                                            localY >= absY && localY <= absY + item.height
                                        }
                                        else -> false
                                    }
                                }
                                selectedItemId = found?.id
                                if (found != null) {
                                    creationMode = CreationMode.ELEMENTO
                                    currentElementType = when (found) {
                                        is TableItem -> ElementType.MESA
                                        is ChairItem -> ElementType.SILLA
                                        is PlantItem -> ElementType.PLANTA
                                        is DoorItem -> ElementType.PUERTA
                                        is WallItem -> ElementType.PARED
                                        else -> currentElementType
                                    }
                                    anchoInput = when (found) {
                                        is TableItem -> found.width.toInt().toString()
                                        is PlantItem -> (found.radius * 2).toInt().toString()
                                        is DoorItem -> found.width.toInt().toString()
                                        is WallItem -> found.width.toInt().toString()
                                        else -> "50"
                                    }
                                    altoInput = when (found) {
                                        is TableItem -> found.height.toInt().toString()
                                        is PlantItem -> (found.radius * 2).toInt().toString()
                                        is DoorItem -> found.height.toInt().toString()
                                        is WallItem -> found.height.toInt().toString()
                                        else -> "50"
                                    }
                                    isConfirmingMedidas = false
                                }
                            }
                        }
                        .pointerInput(anchoInput, altoInput, scale, offset, officeWidth, officeHeight) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                selectedItemId?.let { id ->
                                    val index = items.indexOfFirst { it.id == id }
                                    if (index != -1) {
                                        val item = items[index]
                                        val s = scale * bestScale
                                        val newRawX = item.x + dragAmount.x / s
                                        val newRawY = item.y + dragAmount.y / s
                                        val snappedX = (newRawX / gridSize).roundToInt() * gridSize
                                        val snappedY = (newRawY / gridSize).roundToInt() * gridSize
                                        val itemW = when (item) {
                                            is TableItem -> item.width
                                            is PlantItem -> item.radius * 2
                                            is DoorItem -> item.width
                                            is WallItem -> item.width
                                            else -> 50f
                                        }
                                        val itemH = when (item) {
                                            is TableItem -> item.height
                                            is PlantItem -> item.radius * 2
                                            is DoorItem -> item.height
                                            is WallItem -> item.height
                                            else -> 50f
                                        }
                                        val clampedX = snappedX.coerceIn(0f, maxOf(0f, officeW - itemW))
                                        val clampedY = snappedY.coerceIn(0f, maxOf(0f, officeH - itemH))

                                        val newItem = when(item) {
                                            is TableItem -> TableItem(item.id, clampedX, clampedY, item.width, item.height, item.isVertical)
                                            is ChairItem -> ChairItem(item.id, clampedX, clampedY, item.number)
                                            is PlantItem -> PlantItem(item.id, clampedX, clampedY, item.radius)
                                            is DoorItem -> DoorItem(item.id, clampedX, clampedY, item.width, item.height, item.isVertical)
                                            is WallItem -> WallItem(item.id, clampedX, clampedY, item.width, item.height, item.isVertical)
                                            else -> item
                                        }
                                        items[index] = newItem
                                    }
                                }
                            }
                        }
                ) {
                    val w = officeWidth
                    val h = officeHeight
                    val centerX = size.width / 2 - w / 2
                    val centerY = size.height / 2 - h / 2

                    // 1. Dibujar Lienzo (Negro)
                    drawRect(Color.Black, Offset(centerX, centerY), Size(w, h))

                    // 2. Cuadrícula decorativa
                    for (i in 0..(h/gridSize).toInt()){
                        drawLine(Color.Gray.copy(0.2f), Offset(centerX, centerY + i*gridSize), Offset(centerX+w, centerY+i*gridSize))
                    }
                    for (i in 0..(w/gridSize).toInt()){
                        drawLine(Color.Gray.copy(0.2f), Offset(centerX + i*gridSize, centerY), Offset(centerX + i*gridSize, centerY+h))
                    }

                    // 3. Dibujar Items
                    items.forEach { item ->
                        val isSelected = item.id == selectedItemId
                        val absRectX = item.x + centerX
                        val absRectY = item.y + centerY
                        
                        when (item) {
                            is TableItem -> {
                                drawRect(Color.White, Offset(absRectX, absRectY), Size(item.width, item.height))
                                if (isSelected) drawRect(Color.Red, Offset(absRectX, absRectY), Size(item.width, item.height), style = Stroke(width = 3f))
                            }
                            is ChairItem -> {
                                val radius = 25f
                                drawCircle(Color.White, radius, Offset(absRectX + radius, absRectY + radius))
                                if (isSelected) drawCircle(Color.Red, radius, Offset(absRectX + radius, absRectY + radius), style = Stroke(width = 3f))
                                drawContext.canvas.nativeCanvas.apply {
                                    val paint = android.graphics.Paint().apply {
                                        color = android.graphics.Color.BLACK
                                        textSize = 25f
                                        textAlign = android.graphics.Paint.Align.CENTER
                                    }
                                    drawText(item.number, absRectX + radius, absRectY + radius + 10, paint)
                                }
                            }
                            is PlantItem -> {
                                val centerX = absRectX + item.radius
                                val centerY = absRectY + item.radius
                                // Círculo principal verde
                                drawCircle(Color(0xFF4CAF50), item.radius, Offset(centerX, centerY))
                                // Borde verde oscuro
                                drawCircle(Color(0xFF2E7D32), item.radius, Offset(centerX, centerY), style = Stroke(width = 3f))
                                // Pequeños círculos decorativos (hojas)
                                val leafRadius = item.radius * 0.2f
                                drawCircle(Color(0xFF66BB6A), leafRadius, Offset(centerX - item.radius * 0.4f, centerY - item.radius * 0.3f))
                                drawCircle(Color(0xFF66BB6A), leafRadius, Offset(centerX + item.radius * 0.4f, centerY - item.radius * 0.3f))
                                drawCircle(Color(0xFF66BB6A), leafRadius, Offset(centerX, centerY + item.radius * 0.3f))
                                if (isSelected) drawCircle(Color.Red, item.radius, Offset(centerX, centerY), style = Stroke(width = 3f))
                            }
                            is DoorItem -> {
                                // Dibujar Puerta: Rectángulo con un arco o línea distintiva
                                drawRect(Color(0xFF8B4513), Offset(absRectX, absRectY), Size(item.width, item.height))
                                // Borde
                                if (isSelected) drawRect(Color.Red, Offset(absRectX, absRectY), Size(item.width, item.height), style = Stroke(width = 3f))
                                // Línea de apertura de puerta
                                drawLine(
                                    color = Color.White,
                                    start = Offset(absRectX, absRectY),
                                    end = Offset(absRectX + item.width, absRectY + item.height),
                                    strokeWidth = 2f
                                )
                            }
                            is WallItem -> {
                                // Dibujar Pared: Rectángulo gris oscuro con textura de líneas
                                drawRect(Color.DarkGray, Offset(absRectX, absRectY), Size(item.width, item.height))
                                if (isSelected) drawRect(Color.Red, Offset(absRectX, absRectY), Size(item.width, item.height), style = Stroke(width = 3f))
                                // Textura de pared (líneas diagonales)
                                val step = 10f
                                for (i in 0..(item.width / step).toInt()) {
                                    drawLine(
                                        Color.Gray,
                                        Offset(absRectX + i * step, absRectY),
                                        Offset(absRectX + i * step + 5f, absRectY + item.height),
                                        strokeWidth = 1f
                                    )
                                }
                            }
                        }
                    }
                }

                // Botón de Borrar (Papelera) - Overlay en la esquina superior derecha del Canvas
                if (selectedItemId != null) {
                    val selectedItem = items.find { it.id == selectedItemId }
                    val canRotate = selectedItem is TableItem || selectedItem is DoorItem || selectedItem is WallItem
                    
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (canRotate) {
                            IconButton(
                                onClick = {
                                    val index = items.indexOfFirst { it.id == selectedItemId }
                                    if (index != -1) {
                                        val item = items[index]
                                        val newItem = when (item) {
                                            is TableItem -> TableItem(item.id, item.x, item.y, item.height, item.width, !item.isVertical)
                                            is DoorItem -> DoorItem(item.id, item.x, item.y, item.height, item.width, !item.isVertical)
                                            is WallItem -> WallItem(item.id, item.x, item.y, item.height, item.width, !item.isVertical)
                                            else -> item
                                        }
                                        items[index] = newItem
                                        // Actualizar inputs si están abiertos
                                        anchoInput = when (newItem) {
                                            is TableItem -> newItem.width.toInt().toString()
                                            is DoorItem -> newItem.width.toInt().toString()
                                            is WallItem -> newItem.width.toInt().toString()
                                            else -> anchoInput
                                        }
                                        altoInput = when (newItem) {
                                            is TableItem -> newItem.height.toInt().toString()
                                            is DoorItem -> newItem.height.toInt().toString()
                                            is WallItem -> newItem.height.toInt().toString()
                                            else -> altoInput
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.8f), CircleShape)
                            ) {
                                Icon(Icons.Default.RotateRight, contentDescription = "Rotar", tint = Color(0xFF070F26))
                            }
                        }

                        IconButton(
                            onClick = {
                                items.removeAll { it.id == selectedItemId }
                                selectedItemId = null
                            },
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.8f), CircleShape)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                        }
                    }
                }

                // Botones de Zoom en la esquina inferior derecha
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { scale = (scale * 1.2f).coerceAtMost(10f) },
                        modifier = Modifier.background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Zoom In", tint = Color(0xFF070F26))
                    }
                    IconButton(
                        onClick = { scale = (scale / 1.2f).coerceAtLeast(0.1f) },
                        modifier = Modifier.background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Zoom Out", tint = Color(0xFF070F26))
                    }
                }
            }
        }
    }
}

@Composable
fun CreationTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF070F26) else Color(0xFFE0E0E0),
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.height(40.dp)
    ) {
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun CrearOficinaPreview() {
    com.example.nttdata.ui.theme.NttDataTheme {
        CrearOficina(
            viewModel = OficinaViewModel(),
            onBack = {}
        )
    }
}
