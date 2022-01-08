package com.ccompany.client

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ccompany.interfaces.CompaniesResponse
import com.ccompany.service.APIClient
import com.ccompany.service.APIInterface
import com.ccompany.service.AuthManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var mBtnMaps: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        mBtnMaps = view.findViewById(R.id.open_in_maps)

        mBtnMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        GlobalScope.launch {
            val response: CompaniesResponse = fetchData(context!!)
            if (response.status) {
                response.data.forEach {
                    println(it.name)
                }
            }
        }

        return view
    }


    private suspend fun fetchData(applicationContext: Context): CompaniesResponse {
        return APIClient.client
            .create(APIInterface::class.java)
            .getCompanies("Bearer ${AuthManager(applicationContext).getToken()}")
    }
}
