package com.example.meks_enginering.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meks_enginering.R;
import com.example.meks_enginering.menu.menu_cat_adapter;

import java.util.ArrayList;

public class order_detail_adapter extends RecyclerView.Adapter<order_detail_adapter.order_viewholder> {

       private ArrayList<order_detail_model> mOrderDetail;

   private OnItemClickListener mListener;
   public  interface OnItemClickListener{
     void onItemClick(int position);
   }

   public void setOnItemClickListner(OnItemClickListener listener){
       mListener=listener;
   }

    public class order_viewholder extends RecyclerView.ViewHolder {
        public ImageView order_image;
        public TextView heading,description;

        public order_viewholder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            order_image=itemView.findViewById(R.id.od_image);
            heading=itemView.findViewById(R.id.od_head);
            description=itemView.findViewById(R.id.od_description);
            // onclick for work cat
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public order_detail_adapter(ArrayList<order_detail_model> OrderDetail){
        mOrderDetail=OrderDetail;
    }
    @NonNull
    @Override
    public order_detail_adapter.order_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_od,parent,false);
        order_viewholder ovh=new order_viewholder(v,mListener);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull order_detail_adapter.order_viewholder holder, int position) {
    order_detail_model currentMenu=mOrderDetail.get(position);
    holder.order_image.setImageResource(currentMenu.getOrder_image());
    holder.heading.setText(currentMenu.getOrder_head());
    holder.description.setText(currentMenu.getOrder_desc());

    }

    @Override
    public int getItemCount() {
        return mOrderDetail.size();
    }


}
