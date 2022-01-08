package com.ccompany.client

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ccompany.client.RecyclerTouchListener.ClickListener
import com.ccompany.interfaces.CompaniesResponse
import com.ccompany.interfaces.Company
import com.ccompany.service.APIClient
import com.ccompany.service.APIInterface
import com.ccompany.service.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var mCompaniesData: List<Company>

    private lateinit var recyclerView: RecyclerView
    private lateinit var companiesAdapter: CompaniesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.addOnItemTouchListener(
            RecyclerTouchListener(
                context,
                recyclerView,
                object : ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val company: Company = mCompaniesData[position]
                        Log.d("HomeFragment", "Company: " + company.name)
                    }

                    override fun onLongClick(view: View, position: Int) {}
                })
        )


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response: CompaniesResponse = fetchData(context!!)
                if (response.status) {
                    mCompaniesData = response.data
                    companiesAdapter = CompaniesAdapter(mCompaniesData)
                    recyclerView.adapter = companiesAdapter
                    companiesAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("HomeFragment", it) }
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
