package com.example.meks_enginering.api;

public interface ApiListener {
    void error(String strApiName, String error);

    void failure(String strApiName, String message);

    void success(String strApiName , Object obj);
}
