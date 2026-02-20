package com.example.nttdata.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GestorSesion(private val dataStore: DataStore<Preferences>) {
    private val ID_USUARIO = longPreferencesKey("id_usuario")
    private val TOKEN = stringPreferencesKey("token_auth")

    val idUsuario: Flow<Long?> = dataStore.data.map { it[ID_USUARIO] }
    val token: Flow<String?> = dataStore.data.map { it[TOKEN] }
    suspend fun guardarSesion(id: Long?, token: String){
        dataStore.edit { preferencias ->
            preferencias[ID_USUARIO] = id as Long
            preferencias[TOKEN] = token
        }
    }

    suspend fun logout(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}