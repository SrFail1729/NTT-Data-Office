package com.example.nttdata.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AndroidSecurePreferences(context: Context) : SecurePreferences {
    // Usar MasterKey en lugar de MasterKeys depreciado si es posible, pero manteniendo el código original por ahora para compatibilidad
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

    override var rememberMe: Boolean
        get() = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_REMEMBER_ME, value).apply()

    override var savedUsername: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    override fun clearUsername() {
        sharedPreferences.edit().remove(KEY_USERNAME).apply()
    }

    override var authToken: String?
        get() = sharedPreferences.getString("auth_token", null)
        set(value) {
            sharedPreferences.edit().putString("auth_token", value).apply()
        }

    override fun logout() {
        sharedPreferences.edit()
            .remove("auth_token")
            .remove("saved_username")
            .apply()
    }
}
