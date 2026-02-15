package com.example.nttdata.data.local

import kotlinx.browser.localStorage

class WasmSecurePreferences : SecurePreferences {
    private val KEY_REMEMBER_ME = "remember_me"
    private val KEY_USERNAME = "saved_username"

    override var rememberMe: Boolean
        get() = localStorage.getItem(KEY_REMEMBER_ME)?.toBoolean() ?: false
        set(value) = localStorage.setItem(KEY_REMEMBER_ME, value.toString())

    override var savedUsername: String?
        get() = localStorage.getItem(KEY_USERNAME)
        set(value) {
            if (value != null) {
                localStorage.setItem(KEY_USERNAME, value)
            } else {
                localStorage.removeItem(KEY_USERNAME)
            }
        }

    override fun clearUsername() {
        localStorage.removeItem(KEY_USERNAME)
    }
}
