package com.manager.btlonappbanhangonline.model;

import java.io.Serializable;

public class NewProduct implements Serializable {
    String id;
    String name;
    String img;
    String price;
    String detail;
    String type;
    public NewProduct(){}
    public NewProduct(String id, String name, String img, String price, String detail, String type) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
        this.detail = detail;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
