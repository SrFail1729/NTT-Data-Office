package com.example.nttdata.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppearanceViewModel : ViewModel() {
    var isDarkMode by mutableStateOf(false)
    var fontScale by mutableStateOf(1.0f)

    fun toggleDarkMode() {
        isDarkMode = !isDarkMode
    }

    fun updateFontScale(scale: Float) {
        fontScale = scale.coerceIn(0.5f, 2.0f)
    }
}
