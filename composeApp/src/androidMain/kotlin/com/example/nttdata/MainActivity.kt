package com.example.nttdata

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore

import com.example.nttdata.data.local.AndroidSecurePreferences
import com.example.nttdata.data.local.crearDataStore
import com.example.nttdata.data.local.dataStoreFileName
import com.example.nttdata.di.DataGraph
import androidx.datastore.preferences.core.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataGraph.instanciaDataStore = this.applicationContext.getDataStore()
        val securePreferences = AndroidSecurePreferences(applicationContext)
        setContent {
            App(securePreferences = securePreferences)
        }
    }
}

fun Context.getDataStore(): DataStore<Preferences> =
    crearDataStore {
        this.filesDir.resolve(dataStoreFileName).absolutePath
    }
