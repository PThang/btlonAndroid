package com.manager.btlonappbanhangonline.model;

import java.io.Serializable;

public class TypeProduct implements Serializable {
    String idType;
    String name;
    String image;

    public TypeProduct() {
    }

    public TypeProduct(String idType, String name, String image) {
        this.idType = idType;
        this.name = name;
        this.image = image;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
