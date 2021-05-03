package com.example.meks_enginering.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.example.meks_enginering.R;
import java.util.ArrayList;

public class admin_dashboard_fragment extends Fragment {
    private admin_dashboard_adapter adminDashAdpater;
    private ArrayList<admin_dashboard_model> mAdminModel;
    private LayoutManager mLayoutManger;
    private RecyclerView mRecyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_dash_fragment, container, false);
        populateList();
        buildRecylcerView(view);
        return view;
    }

    public void buildRecylcerView(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.admin_dash_recyclerview);
        mRecyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        mLayoutManger = new GridLayoutManager(getContext(), 2);
        adminDashAdpater = new admin_dashboard_adapter(this.mAdminModel);
        mRecyclerView.setLayoutManager(this.mLayoutManger);
        mRecyclerView.setAdapter(this.adminDashAdpater);
    }

    public void populateList() {
        ArrayList arrayList = new ArrayList();
        mAdminModel = arrayList;
        String str = "3 new request";
        arrayList.add(new admin_dashboard_model(R.mipmap.plumbing, "Plumbing", str));
        mAdminModel.add(new admin_dashboard_model(R.mipmap.electric_installation, "Electric Installation", str));
        mAdminModel.add(new admin_dashboard_model(R.mipmap.et_repair, "Electric Repair", str));
        mAdminModel.add(new admin_dashboard_model(R.mipmap.automobile, "Automobile", str));
        mAdminModel.add(new admin_dashboard_model(R.mipmap.request_admin, "Requests", str));
        mAdminModel.add(new admin_dashboard_model(R.mipmap.msg_admin, "Chat", str));
    }
}
