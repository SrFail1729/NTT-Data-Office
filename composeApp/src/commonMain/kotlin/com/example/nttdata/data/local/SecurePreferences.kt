package com.example.nttdata.data.local

interface SecurePreferences {
    var rememberMe: Boolean
    var savedUsername: String?
    var authToken: String?
    fun clearUsername()
    fun logout()
}
