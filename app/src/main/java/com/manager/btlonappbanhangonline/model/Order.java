package com.manager.btlonappbanhangonline.model;

import java.util.List;

public class Order {
    String id;
    List<Cart> carts;
    String orderDate;

    public Order(String id, List<Cart> carts, String orderDate) {
        this.id = id;
        this.carts = carts;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
