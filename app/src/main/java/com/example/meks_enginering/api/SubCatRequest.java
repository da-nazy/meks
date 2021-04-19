package com.example.meks_enginering.api;

import java.util.ArrayList;

public class SubCatRequest {
    ArrayList<data> data;
    String response_code;
    String response_desc;

    public String getResponse_code() {
        return this.response_code;
    }

    public String getResponse_desc() {
        return this.response_desc;
    }

    public ArrayList<data> getData() {
        return this.data;
    }
}
