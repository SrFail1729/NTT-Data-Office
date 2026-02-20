package com.example.nttdata.domain.repository

import com.example.nttdata.domain.model.Usuario

interface AuthRepository {
    suspend fun login(correo: String, contrasenya: String): Result<Usuario>
}