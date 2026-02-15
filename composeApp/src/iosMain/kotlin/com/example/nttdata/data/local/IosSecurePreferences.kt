package com.example.nttdata.data.local

import platform.Foundation.NSUserDefaults

class IosSecurePreferences : SecurePreferences {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    
    private val KEY_REMEMBER_ME = "remember_me"
    private val KEY_USERNAME = "saved_username"

    override var rememberMe: Boolean
        get() = userDefaults.boolForKey(KEY_REMEMBER_ME)
        set(value) = userDefaults.setBool(value, KEY_REMEMBER_ME)

    override var savedUsername: String?
        get() = userDefaults.stringForKey(KEY_USERNAME)
        set(value) = userDefaults.setObject(value, KEY_USERNAME)

    override fun clearUsername() {
        userDefaults.removeObjectForKey(KEY_USERNAME)
    }
}
