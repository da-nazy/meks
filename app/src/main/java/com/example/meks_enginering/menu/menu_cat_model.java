package com.example.meks_enginering.menu;

public class menu_cat_model {
    private String cat_text;
    private int cat_image;

    public menu_cat_model(String cat_text, int cat_image, String s) {
        this.cat_text = cat_text;
        this.cat_image = cat_image;
    }

    public String getCat_text() {
        return cat_text;
    }

    public int getCat_image() {
        return cat_image;
    }
}
