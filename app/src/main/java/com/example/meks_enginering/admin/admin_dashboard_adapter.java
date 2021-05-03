package com.example.meks_enginering.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.meks_enginering.R;
import java.util.ArrayList;

public class admin_dashboard_adapter extends Adapter<admin_dashboard_adapter.adminHolder> {
    ArrayList<admin_dashboard_model> mModel;

    public class adminHolder extends ViewHolder {
        private ImageView image;
        private TextView job_name;
        private TextView request_count;

        public adminHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.job_image);
            job_name = (TextView) itemView.findViewById(R.id.job_type);
            request_count = (TextView) itemView.findViewById(R.id.request_count);
        }
    }

    public admin_dashboard_adapter(ArrayList<admin_dashboard_model> model) {
        this.mModel = model;
    }

    public adminHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new adminHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_menu_ui, parent, false));
    }

    public void onBindViewHolder(adminHolder holder, int position) {
        admin_dashboard_model currentMenu = (admin_dashboard_model) this.mModel.get(position);
        holder.image.setImageResource(currentMenu.getImage());
        holder.request_count.setText(currentMenu.getRequest_count());
        holder.job_name.setText(currentMenu.getJob_type());
    }

    public int getItemCount() {
        return this.mModel.size();
    }
}
