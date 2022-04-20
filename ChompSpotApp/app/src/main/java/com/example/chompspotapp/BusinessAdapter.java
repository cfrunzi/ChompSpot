package com.example.chompspotapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BusinessAdapter extends ArrayAdapter<Business> implements Filterable{

    List<Business> objects, filteredObject;
    CustomFilter cs;

    public BusinessAdapter(@NonNull Context context, int resource, @NonNull List<Business> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.filteredObject = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.business_list_layout, parent, false);
        }

        Business business = getItem(position);

        Button busName = convertView.findViewById(R.id.businessName2);
        TextView busType = convertView.findViewById(R.id.businessType2);
        TextView busHours = convertView.findViewById(R.id.businessHours2);
        TextView busDist = convertView.findViewById(R.id.businessDist2);
        TextView busBusyText = convertView.findViewById(R.id.businessBusyText2);
        TextView busBusyCircle = convertView.findViewById(R.id.businessCircle);

        busName.setText(business.getName());
        busName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() instanceof businessAdapterListener) {
                    businessAdapterListener bAL = (businessAdapterListener) getContext();
                    bAL.goToMap(business);
                }
                else
                    throw new RuntimeException(getContext() + " must implement businessAdapterListener.");
            }
        });
        busType.setText(business.getType());
        busHours.setText(business.getHours());
        busDist.setText(business.getDistance() + " mi");
        busBusyText.setText(business.getBusy().toUpperCase());

        if (business.getBusy().equals("busy"))
            busBusyCircle.setTextColor(Color.parseColor("#D12B1F"));
        else if (business.getBusy().equals("moderate"))
            busBusyCircle.setTextColor(Color.parseColor("#E8D633"));
        else
            busBusyCircle.setTextColor(Color.parseColor("#3CBD41"));

        return convertView;
    }

    @Override
    public Filter getFilter(){
        if (cs == null)
            cs = new CustomFilter();
        return cs;
    }

    @Override
    public int getCount() {
        return filteredObject.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Nullable
    @Override
    public Business getItem(int position) {
        return filteredObject.get(position);
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                charSequence = charSequence.toString().toUpperCase();
                List<Business> businesses = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i).getName().toUpperCase().contains(charSequence.toString().toUpperCase())) {
                        Business business = objects.get(i).copy();
                        businesses.add(business);
                    }

                }
                results.count = businesses.size();
                results.values = businesses;
            } else {
                results.count = objects.size();
                results.values = objects;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredObject = (List<Business>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    interface businessAdapterListener {
        void goToMap(Business bus);
    }
}
