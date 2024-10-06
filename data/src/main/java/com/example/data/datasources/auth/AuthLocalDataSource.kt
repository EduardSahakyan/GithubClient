package com.example.data.datasources.auth

import android.content.SharedPreferences
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val sPref: SharedPreferences
) {

    fun changeToken(token: String?) {
        sPref.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sPref.getString(KEY_TOKEN, null)
    }

    fun changeAuthState(isSignedIn: Boolean) {
        sPref.edit().putBoolean(KEY_AUTH_STATE, isSignedIn).apply()
    }

    fun isSignedIn(): Boolean {
        return sPref.getBoolean(KEY_AUTH_STATE, false)
    }

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_AUTH_STATE = "auth_state"
    }

}