package com.ccompany.client

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ccompany.client.CompaniesAdapter.CompanyViewHolder
import com.ccompany.interfaces.Company

class CompaniesAdapter(private val companiesList: List<Company>) : RecyclerView.Adapter<CompanyViewHolder>() {
    class CompanyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var description: TextView
        var image: ImageView

        init {
            name = view.findViewById(R.id.name)
            description = view.findViewById(R.id.description)
            image = view.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.company_row, parent, false)
        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companiesList[position]
        holder.name.text = company.name
        holder.description.text = company.description
        val imageBytes = Base64.decode(company.logo.split(',')[1], Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.image.setImageBitmap(decodedImage)
        holder.image.clipToOutline = true
    }

    override fun getItemCount(): Int {
        return companiesList.size
    }
}
