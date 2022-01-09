package com.ccompany.client

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ccompany.interfaces.Company
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class CompanyFragment : Fragment() {
    private lateinit var company: Company

    private lateinit var companyName: TextView
    private lateinit var companyAddress: TextView
    private lateinit var companyDescription: TextView
    private lateinit var companyPhone: ImageView
    private lateinit var companyLogo: ImageView
    private lateinit var companyGeolocation: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        if (bundle == null) {
            Toast.makeText(context, "Please select a valid company", Toast.LENGTH_LONG).show()
        }
        company = bundle?.getParcelable("company")!!

        val view: View = setupUI(inflater, container)

        return view
    }

    private fun setupUI(inflater: LayoutInflater, container: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.fragment_company, container, false)

        companyName = view.findViewById(R.id.textView_company_name)
        companyAddress = view.findViewById(R.id.textView_company_address)
        companyDescription = view.findViewById(R.id.textView_company_description)
        companyPhone = view.findViewById(R.id.imageView_company_phone)
        companyLogo = view.findViewById(R.id.imageView_company_logo)
        companyGeolocation = view.findViewById(R.id.button_open_in_maps)

        companyName.text = company.name
        companyAddress.text = company.address
        companyDescription.text = company.description

        companyPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${company.phone}")
            startActivity(intent)
        }

        val imageBytes = Base64.decode(company.logo.split(',')[1], Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        companyLogo.setImageBitmap(decodedImage)
        companyLogo.clipToOutline = true

        companyGeolocation.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "geo:0,0?q=" + company.geolocation.latitude + company.geolocation.longitude.toString() + "(${company.name})"
                    )
                )
            )
        }

        return view
    }

}
