package com.ccompany.client

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    private lateinit var mBtnLogin: Button
    private lateinit var mBtnMaps: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        mBtnLogin = view.findViewById(R.id.login)
        mBtnMaps = view.findViewById(R.id.open_in_maps)

        mBtnLogin.setOnClickListener {
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
        }
        mBtnMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
