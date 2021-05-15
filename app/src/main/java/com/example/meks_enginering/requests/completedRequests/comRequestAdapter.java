package com.example.meks_enginering.requests.completedRequests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meks_enginering.R;

import java.util.ArrayList;

public class comRequestAdapter extends RecyclerView.Adapter<comRequestAdapter.comRequestHolder> {

    private ArrayList<comRequestModel> comModel;

    public comRequestAdapter(ArrayList<comRequestModel> requestModel){
        comModel=requestModel;
    }
    public class comRequestHolder extends RecyclerView.ViewHolder {
        TextView category_name,service;
        public comRequestHolder(@NonNull View itemView) {
            super(itemView);
            category_name=itemView.findViewById(R.id.category_com);
            service=itemView.findViewById(R.id.service_name_com);
        }
    }

    @NonNull
    @Override
    public comRequestAdapter.comRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_ui,parent,false);
     comRequestHolder crh=new comRequestHolder(v);
       return crh;
    }

    @Override
    public void onBindViewHolder(@NonNull comRequestAdapter.comRequestHolder holder, int position) {
    comRequestModel currentRequest=comModel.get(position);
    holder.category_name.setText(currentRequest.getService_category());
    holder.service.setText(currentRequest.getService_name());
    }

    @Override
    public int getItemCount() {
        return comModel.size();
    }


}
