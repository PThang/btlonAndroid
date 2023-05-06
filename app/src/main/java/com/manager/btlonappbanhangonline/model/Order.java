package com.manager.btlonappbanhangonline.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable  {
    List<Cart> carts;
    String orderDate;
    Long cost;

    public Order(List<Cart> carts, String orderDate, Long cost) {
        this.carts = carts;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Order() {
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
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
