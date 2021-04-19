package com.example.meks_enginering.api;

public class employeeData {
    String account_status;
    String account_type;
    String email_address;
    String firstname;
    String location;
    String othername;
    String phone_number;
    String profile_pic;
    String reg_date;
    String surname;
    String userid;

    public String getUserid() {
        return this.userid;
    }

    public String getEmail_address() {
        return this.email_address;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getOthername() {
        return this.othername;
    }

    public String getProfile_pic() {
        return this.profile_pic;
    }

    public String getPhone_number() {
        return this.phone_number;
    }

    public String getLocation() {
        return this.location;
    }

    public String getAccount_status() {
        return this.account_status;
    }

    public String getReg_date() {
        return this.reg_date;
    }

    public String getAccount_type() {
        return this.account_type;
    }
}
