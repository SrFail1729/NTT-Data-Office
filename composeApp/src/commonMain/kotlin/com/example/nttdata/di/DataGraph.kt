package com.example.nttdata.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.nttdata.data.local.GestorSesion
import com.example.nttdata.data.remote.NttDataApi
import kotlin.getValue
import com.example.nttdata.data.remote.cliente
import com.example.nttdata.data.repository.AuthRepositoryImpl
import com.example.nttdata.data.repository.CitasRepositoryImpl
import com.example.nttdata.domain.repository.AuthRepository
import com.example.nttdata.domain.repository.CitaRepository

object DataGraph {
    lateinit var instanciaDataStore: DataStore<Preferences>
    private val nttDataApi by lazy { NttDataApi(cliente) }
    private val sesion by lazy { GestorSesion(instanciaDataStore) }

    val authRepository : AuthRepository by lazy {
        AuthRepositoryImpl(api = nttDataApi, sesion)
    }

    val citaRepository: CitaRepository by lazy {
        CitasRepositoryImpl(
            nttDataApi,
            sesion
        )
    }
    val gestorSesionPublico: GestorSesion get() = sesion
}