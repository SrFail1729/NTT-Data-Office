package com.example.nttdata.data.local

interface SecurePreferences {
    var rememberMe: Boolean
    var savedUsername: String?
    fun clearUsername()
}
