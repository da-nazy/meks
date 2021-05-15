package com.example.meks_enginering.api;

import java.util.ArrayList;

public class pendingCustomerRequest {
    String response_code;
    String response_desc;
    ArrayList<pendingCustomerData> data;

    public String getResponse_code() {
        return response_code;
    }

    public String getResponse_desc() {
        return response_desc;
    }

    public ArrayList<pendingCustomerData> getData() {
        return data;
    }
}
