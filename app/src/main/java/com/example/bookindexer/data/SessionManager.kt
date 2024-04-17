package com.example.bookindexer.data

import android.content.Context
import android.content.SharedPreferences
import com.example.bookindexer.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS = "user_token"

    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(ACCESS, "Bearer $token")
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(ACCESS, null)
    }


}