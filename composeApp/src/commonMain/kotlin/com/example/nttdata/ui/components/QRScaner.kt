package com.example.nttdata.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun QrScanner(
    modifier: Modifier,
    onQrDetected: (String) -> Unit
)