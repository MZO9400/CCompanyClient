package com.ccompany.client

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ccompany.interfaces.IAuthPage
import com.ccompany.service.AuthManager
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class AuthActivity : AppCompatActivity(), IAuthPage {
    private val fragmentManager = supportFragmentManager
    private var fragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (AuthManager(applicationContext).isLoggedIn()) {
            Toast.makeText(applicationContext, "Already logged in", Toast.LENGTH_LONG).show()
            finish()
        }

        fragmentTransaction.add(R.id.authFragment, LoginFragment())
        fragmentTransaction.commit()
    }

    override fun switchToRegistration(bundle: Bundle?, addToBack: Boolean?) {
        fragmentTransaction = fragmentManager.beginTransaction()
        val registrationFragment = RegistrationFragment()
        registrationFragment.arguments = bundle
        fragmentTransaction.replace(R.id.authFragment, registrationFragment)
        if (addToBack == true)
            fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun switchToLogin(bundle: Bundle?, addToBack: Boolean?) {
        fragmentTransaction = fragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        loginFragment.arguments = bundle
        fragmentTransaction.replace(R.id.authFragment, loginFragment)
        if (addToBack == true)
            fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
