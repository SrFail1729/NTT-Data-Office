package com.example.nttdata.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nttdata.ui.theme.components.BarraInferiorComun
import com.example.nttdata.ui.theme.components.HeaderComun

@Composable
fun PerfilUsuario(
    onBack: () -> Unit
){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Scaffold(
            topBar = {
                HeaderComun(
                    onBack = onBack
                )
            },
            bottomBar ={
                BarraInferiorComun(
                    onMenuClick = {},
                    onBack = onBack
                )
            }
        ) {
            innerPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {


            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaReserva() {
    PerfilUsuario {}
}