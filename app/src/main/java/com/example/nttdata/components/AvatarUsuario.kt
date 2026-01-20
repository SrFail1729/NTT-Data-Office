package com.example.nttdata.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun AvatarUsuario(
    foto: Int,
    tamanyo: Dp,
    modifier: Modifier
){
    Image(
        painter = painterResource(id = foto),
        contentDescription = "Avatar",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(tamanyo)
            .clip(CircleShape)
    )
}