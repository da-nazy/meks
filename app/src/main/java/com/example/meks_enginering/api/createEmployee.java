package com.example.meks_enginering.api;

public class createEmployee {
    String account_type;
    String email;
    String firstname;
    String gender;
    String othername;
    String response_code;
    String response_desc;
    String userid;

    public String getUserid() {
        return this.userid;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getOthername() {
        return this.othername;
    }

    public String getGender() {
        return this.gender;
    }

    public String getAccount_type() {
        return this.account_type;
    }

    public String getResponse_code() {
        return this.response_code;
    }

    public String getResponse_desc() {
        return this.response_desc;
    }
}
