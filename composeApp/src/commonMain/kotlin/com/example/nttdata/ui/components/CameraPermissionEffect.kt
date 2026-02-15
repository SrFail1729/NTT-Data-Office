package com.example.nttdata.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun CameraPermissionEffect(
    shouldAskPermission: Boolean,
    onPermissionResult: (Boolean) -> Unit
)
