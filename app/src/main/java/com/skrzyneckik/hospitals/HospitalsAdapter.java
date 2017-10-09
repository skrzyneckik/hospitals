package com.skrzyneckik.hospitals;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skrzyneckik.domain.Hospital;

import java.util.Collections;
import java.util.List;

/**
 * Created by kskrzynecki on 09/10/2017.
 */

public class HospitalsAdapter extends RecyclerView.Adapter<HospitalsAdapter.HospitalViewHolder> {

    private List<Hospital> mHospitals;

    public HospitalsAdapter() {
        mHospitals = Collections.emptyList();
    }

    @Override
    public HospitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hospital, parent, false);
        HospitalViewHolder vh = new HospitalViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HospitalViewHolder holder, int position) {
        Hospital hospital = mHospitals.get(position);
        holder.nameView.setText(hospital.organisationName());
    }

    @Override
    public int getItemCount() {
        return mHospitals.size();
    }

    public void update(@NonNull List<Hospital> hospitals) {
        this.mHospitals = hospitals;
        notifyDataSetChanged();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView;

        public HospitalViewHolder(View view) {
            super(view);
            nameView = itemView.findViewById(R.id.hospital_name);
        }
    }
}
