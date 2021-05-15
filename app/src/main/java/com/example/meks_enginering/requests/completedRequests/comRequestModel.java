package com.example.meks_enginering.requests.completedRequests;

public class comRequestModel {
    String service_category;
    String service_name;

    public comRequestModel(String service_category, String service_name) {
        this.service_category = service_category;
        this.service_name = service_name;
    }

    public String getService_category() {
        return service_category;
    }

    public void setService_category(String service_category) {
        this.service_category = service_category;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}
