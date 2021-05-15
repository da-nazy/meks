package com.example.meks_enginering.admin.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.meks_enginering.R;
import java.util.ArrayList;

public class employee_list_adapter extends Adapter<employee_list_adapter.employeeHolder> {
    private OnItemClickListener mListener;
    private ArrayList<employee_list_model> mModel;

    public interface OnItemClickListener {
        void onOptionClick(int i, View view);
    }

    public class employeeHolder extends ViewHolder {
        private TextView jobName;
        private TextView name;
        private ImageView option;

        public employeeHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.category_com);
            this.jobName = (TextView) itemView.findViewById(R.id.service_name_pending);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.emp_opt);
            this.option = imageView;

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (listener != null) {
                        int position = employeeHolder.this.getAdapterPosition();
                        if (position != -1) {
                            listener.onOptionClick(position, employeeHolder.this.option);
                        }
                    }
                }
            });
        }
    }

    public void SetOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public employee_list_adapter(ArrayList<employee_list_model> model) {
        this.mModel = model;
    }

    public employeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new employeeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_ui, parent, false), this.mListener);
    }

    public void onBindViewHolder(employeeHolder holder, int position) {
        employee_list_model currentModel = (employee_list_model) this.mModel.get(position);
        holder.name.setText(currentModel.getFullName());
        holder.jobName.setText(currentModel.getJobName());
    }

    public int getItemCount() {
        return this.mModel.size();
    }
}
