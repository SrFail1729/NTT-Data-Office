package com.example.nttdata.data.network

import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.dto.response.LoginResponseDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val URL_API = "https://bfsjffx2qh.execute-api.us-east-1.amazonaws./"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(URL_API)
    .build()

interface NttDataApiAuth{
    @POST("login")
    suspend fun login(@Body request: LoginRequestDTO): Response<LoginResponseDTO>
}

object RetrofitServiceFactory {
    fun makeAuthService(): NttDataApiAuth {
        return retrofit.create(NttDataApiAuth::class.java)
    }
}