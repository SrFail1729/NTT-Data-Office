package com.example.nttdata.domain.model

enum class UserRole {
    ADMIN,
    WORKER
}

data class Office(
    val name: String,
    val items: List<OfficeItem>,
    val customWidth: Float = 800f,
    val customHeight: Float = 400f
)

abstract class OfficeItem(
    val id: Int,
    var x: Float,
    var y: Float
)

class TableItem(
    id: Int,
    x: Float,
    y: Float,
    val width: Float = 120f,
    val height: Float = 60f,
    val isVertical: Boolean = false
) : OfficeItem(id, x, y)

class ChairItem(
    id: Int,
    x: Float,
    y: Float,
    val number: String
) : OfficeItem(id, x, y)

class PlantItem(
    id: Int,
    x: Float,
    y: Float,
    val radius: Float = 30f
) : OfficeItem(id, x, y)

class DoorItem(
    id: Int,
    x: Float,
    y: Float,
    val width: Float = 60f,
    val height: Float = 10f,
    val isVertical: Boolean = false
) : OfficeItem(id, x, y)

class WallItem(
    id: Int,
    x: Float,
    y: Float,
    val width: Float = 100f,
    val height: Float = 10f,
    val isVertical: Boolean = false
) : OfficeItem(id, x, y)

