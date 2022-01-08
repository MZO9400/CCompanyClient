package com.ccompany.client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ccompany.interfaces.Company;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CompaniesAdapter extends
        RecyclerView.Adapter<CompaniesAdapter.CompanyViewHolder> {
    final private List<Company> companiesList;
    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, address;
        public ImageView phone, image;
        public CompanyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            address = view.findViewById(R.id.address);
            phone = view.findViewById(R.id.phone);
            image = view.findViewById(R.id.image);
        }
    }
    public CompaniesAdapter(List<Company> companiesList) {
        this.companiesList = companiesList;
    }
    @NotNull
    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_row, parent, false);
        return new CompanyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        Company company = companiesList.get(position);
        holder.name.setText(company.getName());
        holder.description.setText(company.getDescription());
        holder.address.setText(company.getAddress());
        holder.image.setImageResource(R.drawable.ic_phone);
    }
    @Override
    public int getItemCount() {
        return companiesList.size();
    }
}
