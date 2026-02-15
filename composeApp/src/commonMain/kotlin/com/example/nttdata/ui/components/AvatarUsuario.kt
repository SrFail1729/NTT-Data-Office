package com.example.nttdata.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun AvatarUsuario(
    foto: DrawableResource,
    tamanyo: Dp,
    modifier: Modifier
){
    Image(
        painter = painterResource(foto),
        contentDescription = "Avatar",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(tamanyo)
            .clip(CircleShape)
    )
}