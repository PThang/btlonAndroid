package com.manager.btlonappbanhangonline.model;

public class Delivery {
    Order order;
    Boolean isReceived;
    String receivedDate;

    public Delivery(Order order, Boolean isReceived, String receivedDate) {
        this.order = order;
        this.isReceived = isReceived;
        this.receivedDate = receivedDate;
    }

    public Delivery() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Boolean getReceived() {
        return isReceived;
    }

    public void setReceived(Boolean received) {
        isReceived = received;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }
}
