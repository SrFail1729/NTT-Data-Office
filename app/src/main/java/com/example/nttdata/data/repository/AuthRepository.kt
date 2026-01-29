package com.example.nttdata.data.repository

import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.dto.response.LoginResponseDTO
import com.example.nttdata.data.network.NttDataApiAuth
import com.example.nttdata.data.network.RetrofitServiceFactory

class AuthRepository(private val api: NttDataApiAuth = RetrofitServiceFactory.makeAuthService()) {

    suspend fun login(request: LoginRequestDTO): Result<LoginResponseDTO> {
        return try {
            val response = api.login(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error en el login: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}