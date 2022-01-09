package com.ccompany.client

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ccompany.client.RecyclerTouchListener.ClickListener
import com.ccompany.interfaces.CompaniesResponse
import com.ccompany.interfaces.Company
import com.ccompany.service.APIClient
import com.ccompany.service.APIInterface
import com.ccompany.service.AuthManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@DelicateCoroutinesApi
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
                        val fragment: Fragment = CompanyFragment()
                        val bundle = Bundle()
                        bundle.putParcelable("company", company)
                        fragment.arguments = bundle
                        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
                        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.homeFragmentView, fragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
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
                    companiesAdapter.notifyItemRangeInserted(0, mCompaniesData.size)
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
