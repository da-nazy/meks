package com.example.meks_enginering;

import com.google.gson.annotations.SerializedName;

public class get_user {
    private long userid;
    private String email_address;
    private String surname;
    private String firstname;
    private String othername;
    private String contact;
    private String gender;
    private String location;
    private String reg_date;
    private String account_type;
    private String response_code;
    private String response_desc;

   // @SerializedName("body")
   // private String text;

    public long getUserid() {
        return userid;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getOthername() {
        return othername;
    }
    public String getContact(){
        return contact;
    }
    public String getLocation(){
        return location;
    }
    public String getGender() {
        return gender;
    }

    public String getReg_date() {
        return reg_date;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getResponse_code() {
        return response_code;
    }

    public String getResponse_desc() {
        return response_desc;
    }

  //  public String getText() {
   //     return text;
   // }
}
