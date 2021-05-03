package com.example.meks_enginering.order;

public class order_detail_model {

    private int order_image;


    private String sub_category_id;
    private String category_id;
    private String sub_category_name;
    private String sub_category_desc;
    private String  order_desc;

    public order_detail_model(int order_image,String sub_category_id,String category_id,String sub_category_desc, String sub_category_name, String order_desc) {
       this.category_id=category_id;
       this.sub_category_id=sub_category_id;
       this.sub_category_desc=sub_category_desc;
        this.order_image = order_image;
        this.sub_category_name = sub_category_name;
        this.order_desc = order_desc;
    }

    public int getOrder_image() {
        return order_image;
    }

    public void setOrder_image(int order_image) {
        this.order_image = order_image;
    }

    public String getOrder_head() {
        return sub_category_name;
    }

    public void setOrder_head(String order_head) {
        this.sub_category_name = order_head;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }
    public String getSub_category_id() {
        return sub_category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public String getSub_category_desc() {
        return sub_category_desc;
    }

}
