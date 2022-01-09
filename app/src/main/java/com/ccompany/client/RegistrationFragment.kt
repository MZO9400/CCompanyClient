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
import com.ccompany.interfaces.RegisterResponse
import com.ccompany.service.AuthManager
import com.ccompany.service.DBService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class RegistrationFragment : Fragment() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_registration, container, false)

        btnLogin = view.findViewById(R.id.open_login_page)
        btnRegister = view.findViewById(R.id.register_button)
        inputEmail = view.findViewById(R.id.username)
        inputPassword = view.findViewById(R.id.password)
        inputName = view.findViewById(R.id.name)

        btnLogin.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("email", inputEmail.text.toString())
            bundle.putString("password", inputPassword.text.toString())
            bundle.putString("name", inputName.text.toString())
            (activity as IAuthPage).switchToLogin(bundle, false)
        }

        btnRegister.setOnClickListener {
            val am = AuthManager(context!!)
            GlobalScope.launch {
                val response: RegisterResponse = am
                    .register(
                        inputEmail.text.toString(),
                        inputPassword.text.toString(),
                        inputName.text.toString()
                    )
                if (response.status) {
                    DBService(context!!).saveUserDetail(
                        inputEmail.text.toString(),
                        inputName.text.toString()
                    )
                    am.saveToken(response.token)
                    Intent(activity, HomeActivity::class.java).apply {
                        activity?.finish()
                    }
                } else {
                    activity?.runOnUiThread {
                        inputEmail.error = response.message
                    }
                }
            }
        }

        val bundle = arguments
        if (bundle != null) {
            inputEmail.setText(bundle.getString("email"))
            inputPassword.setText(bundle.getString("password"))
            inputName.setText(bundle.getString("name"))
        }
        return view
    }
}
