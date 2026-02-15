package com.example.nttdata.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun CameraPermissionEffect(
    shouldAskPermission: Boolean,
    onPermissionResult: (Boolean) -> Unit
) {
    // For now, we assume permission is granted or not needed on iOS stub
    LaunchedEffect(shouldAskPermission) {
        if (shouldAskPermission) {
            onPermissionResult(true) 
        }
    }
}
