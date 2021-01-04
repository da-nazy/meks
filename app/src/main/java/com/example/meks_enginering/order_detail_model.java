package com.example.meks_enginering;

public class order_detail_model {

    private int order_image;
    private String order_head;
    private String  order_desc;

    public order_detail_model(int order_image, String order_head, String order_desc) {
        this.order_image = order_image;
        this.order_head = order_head;
        this.order_desc = order_desc;
    }

    public int getOrder_image() {
        return order_image;
    }

    public void setOrder_image(int order_image) {
        this.order_image = order_image;
    }

    public String getOrder_head() {
        return order_head;
    }

    public void setOrder_head(String order_head) {
        this.order_head = order_head;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }
}
