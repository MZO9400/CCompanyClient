package com.ccompany.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccompany.service.AuthManager
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!AuthManager(applicationContext).isLoggedIn()) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_home)

        val fragment = HomeFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.homeFragmentView, fragment)
        fragmentTransaction.commit()
    }
}
