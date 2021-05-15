package com.example.meks_enginering.requests.pendingRequests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meks_enginering.R;

import java.util.ArrayList;

public class pendRequestAdapter extends RecyclerView.Adapter<pendRequestAdapter.requestHolder>{
    //Initialize the model
    private ArrayList<pendRequestModel> requestModel;

    // Initialize the constructor
    public pendRequestAdapter(ArrayList<pendRequestModel> requestModel){
        this.requestModel=requestModel;
    }

    public class requestHolder extends RecyclerView.ViewHolder {
        TextView category,service_name,assigned;
        public requestHolder(@NonNull View itemView) {
            super(itemView);

            category=itemView.findViewById(R.id.category_com);
            service_name=itemView.findViewById(R.id.service_name_pending);
            assigned=itemView.findViewById(R.id.assigned_pending);

        }
    }

    @NonNull
    @Override
    public pendRequestAdapter.requestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_order_ui,parent,false);
       requestHolder rqh=new requestHolder(v);
        return rqh;
    }

    @Override
    public void onBindViewHolder(@NonNull pendRequestAdapter.requestHolder holder, int position) {
    pendRequestModel currentReq=requestModel.get(position);
    holder.category.setText(currentReq.category);
    holder.service_name.setText(currentReq.service_name);

  try{
      if(currentReq.date_assigned!=null||currentReq.date_assigned.isEmpty()){
          holder.assigned.setText("Assigned");
          // Should change the color to green
      }else{
          holder.assigned.setText("Not Assigned");
      }
  }catch(Exception e){
      e.printStackTrace();
  }

    }

    @Override
    public int getItemCount() {
        return requestModel.size();
    }


}
