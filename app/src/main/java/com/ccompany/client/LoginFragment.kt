package com.ccompany.client

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.ccompany.interfaces.IAuthPage
import com.ccompany.interfaces.LoginResponse
import com.ccompany.service.AuthManager
import com.ccompany.service.DBService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class LoginFragment : Fragment() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        btnLogin = view.findViewById(R.id.login_button)
        btnRegister = view.findViewById(R.id.open_registration_page)
        inputEmail = view.findViewById(R.id.username)
        inputPassword = view.findViewById(R.id.password)

        btnRegister.setOnClickListener {
            val loginData = Bundle()
            loginData.putString("email", inputEmail.text.toString())
            loginData.putString("password", inputPassword.text.toString())
            (activity as IAuthPage).switchToRegistration(loginData, false)
        }

        btnLogin.setOnClickListener {
            val am = AuthManager(context!!)
            GlobalScope.launch {
                try {
                    val response: LoginResponse = am.login(inputEmail.text.toString(), inputPassword.text.toString())
                    if (!response.status) {
                        activity?.runOnUiThread {
                            inputEmail.error = response.message
                        }
                        return@launch
                    }
                    DBService(context!!).saveUserDetail(response.user.name, response.user.email)
                    am.saveToken(response.token)
                    Intent(activity, HomeActivity::class.java).apply {
                        activity?.finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val bundle = arguments
        if (bundle != null) {
            inputEmail.setText(bundle.getString("email"))
            inputPassword.setText(bundle.getString("password"))
        }

        return view
    }
}
