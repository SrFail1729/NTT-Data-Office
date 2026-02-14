package com.example.nttdata.ui.screens.OficinaSelection

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.nttdata.domain.model.ChairItem
import com.example.nttdata.domain.model.DoorItem
import com.example.nttdata.domain.model.Office
import com.example.nttdata.domain.model.OfficeItem
import com.example.nttdata.domain.model.PlantItem
import com.example.nttdata.domain.model.TableItem
import com.example.nttdata.domain.model.WallItem

class OficinaViewModel : ViewModel() {
    private val _oficinas = mutableStateListOf<Office>()
    val oficinas: List<Office> get() = _oficinas

    init {
        val defaultItems = mutableListOf<OfficeItem>()
        var nextId = 1
        
        // Paredes Exteriores
        defaultItems.add(WallItem(nextId++, 0f, 0f, 1200f, 10f)) // Norte
        defaultItems.add(WallItem(nextId++, 0f, 790f, 1200f, 10f)) // Sur
        defaultItems.add(WallItem(nextId++, 0f, 0f, 10f, 800f, true)) // Oeste
        defaultItems.add(WallItem(nextId++, 1190f, 0f, 10f, 800f, true)) // Este
        
        // Puerta
        defaultItems.add(DoorItem(nextId++, 600f, 790f, 80f, 10f))
        
        // Plantas decorativas
        defaultItems.add(PlantItem(nextId++, 50f, 50f, 30f))
        defaultItems.add(PlantItem(nextId++, 1120f, 50f, 30f))
        defaultItems.add(PlantItem(nextId++, 50f, 710f, 30f))
        defaultItems.add(PlantItem(nextId++, 1120f, 710f, 30f))
        
        // 20 Puestos de trabajo (4 filas x 5 columnas)
        for (row in 0..3) {
            for (col in 0..4) {
                val x = 150f + col * 200f
                val y = 150f + row * 150f
                
                // Mesa
                defaultItems.add(TableItem(nextId++, x, y, 120f, 60f))
                // Silla (Puesto)
                val seatNumber = "${(row * 5) + col + 1}"
                defaultItems.add(ChairItem(nextId++, x + 35f, y - 55f, seatNumber))
            }
        }

        _oficinas.add(
            Office(
                name = "Oficina Central NTT Data",
                items = defaultItems,
                customWidth = 1200f,
                customHeight = 800f
            )
        )
    }

    fun guardarOficina(office: Office) {
        _oficinas.add(office)
    }

    fun eliminarOficina(office: Office) {
        _oficinas.remove(office)
    }
}
