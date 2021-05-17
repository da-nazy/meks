package com.example.meks_enginering.admin.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.example.meks_enginering.R;
import com.example.meks_enginering.admin.create_employee;
import com.example.meks_enginering.admin.fragment.employee_list_adapter.OnItemClickListener;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.deleteEmployee;
import com.example.meks_enginering.api.employeeData;
import com.example.meks_enginering.api.getEmployee;
import com.example.meks_enginering.utility.urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class employees_fragment extends Fragment implements ApiListener {
    int anchorPosition;
    View anchorView;
    Button cancel;
    Button delete;
    private employee_list_adapter empAdapter;
    getEmployee employee = new getEmployee();
    FloatingActionButton fab;
    private LayoutManager mLayout;
    private ArrayList<employee_list_model> mModel;
    private RecyclerView mRecyclerView;
    MeksApi meksApi;
    int postion;
    Retrofit retrofit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.employee_list, container, false);
        gSon();
        try {
            getEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        populateList();
        buildRecyclerView(main);
    fab = main.findViewById(R.id.add_employee);

       fab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
          startActivity(new Intent(getActivity(), create_employee.class));
            }
        });
        this.empAdapter.SetOnItemClickListener(new OnItemClickListener() {
            public void onOptionClick(int position, View v) {
              anchorView = v;
             anchorPosition = position;
              getDetail();
            }
        });
        return main;
    }

    private void buildRecyclerView(View v) {
      mRecyclerView= v.findViewById(R.id.employee_list_recycle);

       mRecyclerView.setHasFixedSize(true);
       mLayout = new LinearLayoutManager(getActivity());
       empAdapter = new employee_list_adapter(this.mModel);
       mRecyclerView.setLayoutManager(this.mLayout);
       mRecyclerView.setAdapter(this.empAdapter);
    }

    private void populateList() {
        mModel=new ArrayList<>();
        mModel.add(new employee_list_model("Name", "Nothing"));
    }

    public void updateList(ArrayList<employee_list_model> model) {
        mModel.clear();
        mModel.addAll(model);
        empAdapter.notifyDataSetChanged();
    }

    public void getDetail() {
        PopupMenu popup = new PopupMenu(this.anchorView.getContext().getApplicationContext(), this.anchorView);
        popup.getMenuInflater().inflate(R.menu.emp_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.emp_delete /*2131230895*/:
                        employees_fragment employees_fragment = employees_fragment.this;
                        postion = employees_fragment.anchorPosition;
                        employees_fragment = employees_fragment.this;
                        deletDialog(employees_fragment.getView());
                        Toast.makeText(getContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.emp_edit /*2131230896*/:
                        Intent update = new Intent(getActivity(), create_employee.class);
                        String userid = mModel.get(anchorPosition).getUserid();
                        String email = mModel.get(anchorPosition).getEmail();
                        ArrayList<String> details = new ArrayList();
                        if (!(userid == null || email == null)) {
                            details.add(userid);
                            details.add(email);
                            details.add("2");
                        }
                        update.putStringArrayListExtra("Operation", details);
                        employees_fragment.this.startActivity(update);
                        break;
                    case R.id.emp_view /*2131230901*/:
                        Toast.makeText(employees_fragment.this.getContext(), "View Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public void getEmployee() throws IOException, InterruptedException {
        if (urls.isConnected()) {
            MeksApi apiList = (MeksApi) this.retrofit.create(MeksApi.class);
            APIResponse.callRetrofit(apiList.getEmployees(true, urls.securityKey()), "getEmployee", getContext(), this);
            return;
        }
        Toast.makeText(getContext(), "No connected", Toast.LENGTH_SHORT).show();
    }

    public void gSon() {
        Retrofit build = new Builder().baseUrl(urls.meks()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create())).build();
        this.retrofit = build;
        this.meksApi = (MeksApi) build.create(MeksApi.class);
    }

    public void success(String strApiName, Object response) {
        if (strApiName.equals("getEmployee")) {
           employee = (getEmployee) response;
            ArrayList<employee_list_model> model = new ArrayList();
            for (int a = 0; a < this.employee.getData().size(); a++) {
                model.add(new employee_list_model(employee.getData().get(a).getSurname() + employee.getData().get(a).getFirstname() + employee.getData().get(a).getOthername(), "Employee",  employee.getData().get(a).getUserid(),employee.getData().get(a).getEmail_address()));
            }
            updateList(model);
        }
        if (strApiName.equals("deleteEmployee") && ((deleteEmployee) response).getResponse_code().equals("00")) {
            try {
                getEmployee();
            } catch (Exception e) {
                Log.i("getemployee", e.getMessage());
            }
        }
    }

    public void error(String strApiName, String error) {
        if (strApiName.equals("getEmployee")) {
            Toast.makeText(getContext(), error,  Toast.LENGTH_SHORT).show();
        }
        if (strApiName.equals("deleteEmployee")) {
            Toast.makeText(getContext(), error,  Toast.LENGTH_SHORT).show();
        }
    }

    public void failure(String strApiName, String message) {
        if (strApiName.equals("getEmployee")) {
            Toast.makeText(getContext(), message,  Toast.LENGTH_SHORT).show();
        }
        if (strApiName.equals("deleteEmployee")) {
            Toast.makeText(getContext(), message,  Toast.LENGTH_SHORT).show();
        }
    }

    public void deletDialog(View view) {
        View v = view;
        View pauseDialog = LayoutInflater.from(getContext()).inflate(R.layout.delete_employee, (ViewGroup) v.findViewById(R.id.content), false);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(pauseDialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
       cancel =  pauseDialog.findViewById(R.id.cancel_emp);
         delete = pauseDialog.findViewById(R.id.delete_emp);
         cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                    employees_fragment employees_fragment = employees_fragment.this;
                    employees_fragment.deleteEmployee((employees_fragment.mModel.get(employees_fragment.this.postion)).getUserid(), (employees_fragment.this.mModel.get(employees_fragment.this.postion)).getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(employees_fragment.this.getContext(), e.getCause() + "\n" + e.getMessage(),  Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteEmployee(String id, String email) throws IOException, InterruptedException {
        if (urls.isConnected()) {
            MeksApi apiList = (MeksApi) this.retrofit.create(MeksApi.class);
            APIResponse.callRetrofit(this.meksApi.deleteEmployee(id, email, urls.securityKey()), "deleteEmployee", getContext(), this);
        }
    }
}
