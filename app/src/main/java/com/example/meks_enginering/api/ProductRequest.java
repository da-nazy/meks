package com.example.meks_enginering.api;

public class ProductRequest {
    String request_id;
    String service_id;
    String customer_id;
    String status;
    String sent_date;
    String last_update;
    String response_code;
    String response_desc;

    public String getRequest_id() {
        return request_id;
    }

    public String getService_id() {
        return service_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getStatus() {
        return status;
    }

    public String getSent_date() {
        return sent_date;
    }

    public String getLast_update() {
        return last_update;
    }

    public String getResponse_code() {
        return response_code;
    }

    public String getResponse_desc() {
        return response_desc;
    }
}
