package com.manager.btlonappbanhangonline.model;

import java.io.Serializable;

public class NewProduct implements Serializable {
    String id;
    String tensp;
    String hinhanh;
    String giasp;
    String mota;
    String loai;

    public NewProduct(String id, String tensp, String hinhanh, String giasp, String mota, String loai) {
        this.id = id;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.giasp = giasp;
        this.mota = mota;
        this.loai = loai;
    }

    public NewProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

}
