package com.example.careermate_app.prefs

import android.content.Context

class SessionManager(private val context: Context) {
    private val prefsName = "careermate_prefs"
    private val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "KEY_TOKEN"
        private const val KEY_USER_ID = "KEY_USER_ID"
        private const val KEY_USERNAME = "KEY_USERNAME"
        private const val KEY_EMAIL = "KEY_EMAIL"
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun fetchAuthToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUser(userId: String, token: String) {
        val editor = prefs.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }
    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)
    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)
    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)
}