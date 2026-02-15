package com.example.nttdata.data.repository

import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.network.AuthServiceFactory
import com.example.nttdata.data.network.NttDataApiAuth
import com.example.nttdata.domain.model.Usuario

class AuthRepository(private val api: NttDataApiAuth = AuthServiceFactory.makeAuthService()) {

    suspend fun login(request: LoginRequestDTO): Result<Usuario> {
        return try {
            val body = api.login(request)

            val usuario = Usuario(
                id = body.idUsuario,
                nombre = body.nombre,
                token = body.token
            )
            Result.success(usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}