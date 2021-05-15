package com.example.meks_enginering.requests.pendingRequests;

import java.util.ArrayList;

public class pendRequestModel {
  String request_id;
  String service_id;
  String status;
  String sent_date;
  String last_updated;
  String category;
  String service_name;
  String date_assigned;

  public pendRequestModel(String request_id, String service_id, String status, String sent_date, String last_update, String category, String service_name, String date_assigned) {
    this.request_id = request_id;
    this.service_id = service_id;
    this.status = status;
    this.sent_date = sent_date;
    this.last_updated = last_update;
    this.category = category;
    this.service_name = service_name;
    this.date_assigned = date_assigned;
  }

  public String getRequest_id() {
    return request_id;
  }

  public void setRequest_id(String request_id) {
    this.request_id = request_id;
  }

  public String getService_id() {
    return service_id;
  }

  public void setService_id(String service_id) {
    this.service_id = service_id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSent_date() {
    return sent_date;
  }

  public void setSent_date(String sent_date) {
    this.sent_date = sent_date;
  }

  public String getLast_update() {
    return last_updated;
  }

  public void setLast_update(String last_update) {
    this.last_updated = last_update;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getService_name() {
    return service_name;
  }

  public void setService_name(String service_name) {
    this.service_name = service_name;
  }

  public String getDate_assigned() {
    return date_assigned;
  }

  public void setDate_assigned(String date_assigned) {
    this.date_assigned = date_assigned;
  }
}
