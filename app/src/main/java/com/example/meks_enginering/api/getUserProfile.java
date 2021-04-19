package com.example.meks_enginering.api;

public class getUserProfile {
    private String account_type;
    private String contact;
    private String email_address;
    private String firstname;
    private String gender;
    private String location;
    private String othername;
    private String profile_pic;
    private String reg_date;
    private String response_code;
    private String response_desc;
    private String surname;
    private long userid;

    public long getUserid() {
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

    public String getContact() {
        return this.contact;
    }

    public String getGender() {
        return this.gender;
    }

    public String getLocation() {
        return this.location;
    }

    public String getReg_date() {
        return this.reg_date;
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

    public String getProfile_pic() {
        return this.profile_pic;
    }
}
