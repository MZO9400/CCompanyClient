package com.ccompany.client

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
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
        var image: ImageView
        var phone: ImageView

        init {
            name = view.findViewById(R.id.name)
            image = view.findViewById(R.id.image)
            phone = view.findViewById(R.id.phone)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.company_row, parent, false)
        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companiesList[position]
        holder.name.text = company.name
        val imageBytes = Base64.decode(company.logo.split(',')[1], Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.image.setImageBitmap(decodedImage)
        holder.image.clipToOutline = true
        holder.phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${company.phone}")
            holder.itemView.context.startActivity(intent)
        }

        val primaryColor: Int = holder.phone.context.getColor(R.color.primaryColor)

        holder.phone.colorFilter = PorterDuffColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP)

    }

    override fun getItemCount(): Int {
        return companiesList.size
    }
}
