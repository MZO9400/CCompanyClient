package com.ccompany.service

import android.content.Context
import android.util.Log
import com.ccompany.interfaces.LoginRequest
import com.ccompany.interfaces.LoginResponse
import com.ccompany.interfaces.RegisterRequest
import com.ccompany.interfaces.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthManager(context: Context) {
    private var mContext: Context? = context

    fun login(username: String, password: String) {
        val client = APIClient.client.create(APIInterface::class.java)
        val request: Call<LoginResponse> = client.login(LoginRequest(username, password))
        request.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        if (loginResponse.status) {
                            saveToken(loginResponse.token)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("AuthManager", "Login response: $t")
            }
        })
    }

    fun register(username: String, password: String, name: String) {
        val client = APIClient.client.create(APIInterface::class.java)
        val request: Call<RegisterResponse> = client.register(RegisterRequest(username, password, name))
        request.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        if (registerResponse.status) {
                            saveToken(registerResponse.token)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("AuthManager", "Register response: $t")
            }
        })
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
