package com.example.nttdata.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
actual fun QrScanner(
    modifier: Modifier,
    onQrDetected: (String) -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text("QR Scanner not implemented on iOS")
    }
}
