package com.ccompany.service

import android.content.Context
import com.auth0.android.jwt.JWT
import com.ccompany.interfaces.LoginRequest
import com.ccompany.interfaces.LoginResponse
import com.ccompany.interfaces.RegisterRequest
import com.ccompany.interfaces.RegisterResponse


class AuthManager(context: Context) {
    private var mContext: Context? = context

    suspend fun login(username: String, password: String): LoginResponse {
        return APIClient.client
            .create(APIInterface::class.java)
            .login(LoginRequest(username, password))
    }

    suspend fun register(username: String, password: String, name: String): RegisterResponse {
        return APIClient.client
            .create(APIInterface::class.java)
            .register(RegisterRequest(username, password, name))
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
        val token: String = getToken()
        if (token == "")
            return false

        val jwt = JWT(token)
        val claim: String = jwt.getClaim("exp").asString() ?: return false
        val exp: Long = claim.toLong()
        val now: Long = System.currentTimeMillis() / 1000

        return exp > now
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
