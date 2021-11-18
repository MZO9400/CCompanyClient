package com.ccompany.auth

import android.content.Context


class AuthManager(context: Context) {
    private var mContext: Context? = context

    fun login(username: String, password: String): Boolean {
        return true
    }

    fun register(username: String, password: String, name: String): Boolean {
        return true
    }

    fun logout(): Boolean {
        return try {
            mContext?.let {
                val sharedPref = it.getSharedPreferences(
                    "auth",
                    Context.MODE_PRIVATE
                )
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isLoggedIn(): Boolean {
        return getToken() != ""
    }

    fun saveToken(token: String) {
        mContext
            ?.applicationContext
            ?.getSharedPreferences("auth", Context.MODE_PRIVATE)
            ?.edit()
            ?.putString("token", token)
            ?.apply()
    }

    fun getToken(): String {
        return mContext
            ?.applicationContext
            ?.getSharedPreferences("auth", Context.MODE_PRIVATE)
            ?.getString("token", "")
            ?: ""
    }
}
