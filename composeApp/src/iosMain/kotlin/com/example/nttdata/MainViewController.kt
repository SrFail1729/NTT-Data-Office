package com.example.nttdata

import androidx.compose.ui.window.ComposeUIViewController

import com.example.nttdata.data.local.IosSecurePreferences

fun MainViewController() = ComposeUIViewController { 
    val securePreferences = IosSecurePreferences()
    App(securePreferences = securePreferences) 
}
