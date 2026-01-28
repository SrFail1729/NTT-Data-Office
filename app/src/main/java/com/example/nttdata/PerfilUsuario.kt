package com.example.nttdata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nttdata.components.BarraInferiorComun
import com.example.nttdata.components.HeaderComun
import com.example.nttdata.components.HeaderReserva
import com.example.nttdata.components.QrScanner

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