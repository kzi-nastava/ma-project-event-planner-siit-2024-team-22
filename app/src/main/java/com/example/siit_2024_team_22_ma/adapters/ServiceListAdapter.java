package com.example.siit_2024_team_22_ma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siit_2024_team_22_ma.R;
import com.example.siit_2024_team_22_ma.models.Service;

import java.util.ArrayList;

public class ServiceListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Service> services;

    public ServiceListAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Инфляция layout из service_card.xml
            convertView = LayoutInflater.from(context).inflate(R.layout.service_card, parent, false);
        }

        ImageView serviceImage = convertView.findViewById(R.id.service_image);
        TextView serviceTitle = convertView.findViewById(R.id.service_title);
        TextView serviceDescription = convertView.findViewById(R.id.service_description);
        TextView serviceCompany = convertView.findViewById(R.id.service_company);

        Service service = services.get(position);

        serviceImage.setImageResource(service.getImageResource());
        serviceTitle.setText(service.getTitle());
        serviceDescription.setText(service.getDescription());
        serviceCompany.setText(service.getCompany());

        return convertView;
    }
}