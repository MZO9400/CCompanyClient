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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class CompanyFragment : Fragment() {
    private lateinit var company: Company

    private lateinit var companyName: TextView
    private lateinit var companyAddress: TextView
    private lateinit var companyPhone: ImageView
    private lateinit var companyLogo: ImageView
    private lateinit var companyGeolocation: Button
    private lateinit var chipGroup: ChipGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough()
        enterTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        if (bundle == null) {
            Toast.makeText(context, "Please select a valid company", Toast.LENGTH_LONG).show()
        }
        company = bundle?.getParcelable("company")!!

        return setupUI(inflater, container)
    }

    private fun setupUI(inflater: LayoutInflater, container: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.fragment_company, container, false)

        companyName = view.findViewById(R.id.textView_company_name)
        companyAddress = view.findViewById(R.id.textView_company_address)
        companyPhone = view.findViewById(R.id.imageView_company_phone)
        companyLogo = view.findViewById(R.id.imageView_company_logo)
        companyGeolocation = view.findViewById(R.id.button_open_in_maps)
        chipGroup = view.findViewById(R.id.chip_group)

        companyName.text = company.name
        companyAddress.text = company.address

        company.description
            .split(",")
            .toMutableList()
            .forEach {
                val chip = Chip(context)
                chip.text = it.replace("_", " ")
                chip.setChipBackgroundColorResource(R.color.secondaryColor)
                chip.setTextColor(resources.getColor(R.color.secondaryTextColor))
                chip.isClickable = false
                chipGroup.addView(chip)
            }


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
