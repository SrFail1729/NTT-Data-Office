package com.example.nttdata.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecurePreferences(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_USERNAME = "saved_username"
    }

    var rememberMe: Boolean
        get() = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_REMEMBER_ME, value).apply()

    var savedUsername: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    fun clearUsername() {
        sharedPreferences.edit().remove(KEY_USERNAME).apply()
    }
}
