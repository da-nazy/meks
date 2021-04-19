package com.example.meks_enginering.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meks_enginering.R;

import java.util.ArrayList;

public class menu_cat_adapter extends RecyclerView.Adapter<menu_cat_adapter.menu_viewholder> {

    private ArrayList<menu_cat_model>mMenuModel;
    // This is for performing click on the list
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);

    }
   public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
   }
    public class menu_viewholder extends RecyclerView.ViewHolder{
        public TextView cat_name;
        public ImageView cat_image;
        public menu_viewholder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            cat_name=itemView.findViewById(R.id.menu_cat_text);
            cat_image=itemView.findViewById(R.id.menu_cat_image);

            // for handling clicks
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

    public menu_cat_adapter(ArrayList<menu_cat_model> menuModel){
        mMenuModel=menuModel;
    }
    @NonNull
    @Override
    public menu_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_grid,parent,false);
        menu_viewholder mvh=new menu_viewholder(v,mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull menu_viewholder holder, int position) {
        menu_cat_model currentMenu=mMenuModel.get(position);
        holder.cat_name.setText(currentMenu.getCat_text());
        holder.cat_image.setImageResource(currentMenu.getCat_image());
    }



    @Override
    public int getItemCount() {
        return mMenuModel.size();
    }


}
