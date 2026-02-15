package com.example.nttdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.nttdata.data.local.AndroidSecurePreferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val securePreferences = AndroidSecurePreferences(applicationContext)
        setContent {
            App(securePreferences = securePreferences)
        }
    }
}
