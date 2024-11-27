package com.example.foodapp.Domain;

import java.util.List;

public class Order {
    private String orderId;
    private List<String> foodsList;
    private double total;
    private double tax;
    private double delivery;

    // Constructor không tham số (Firebase yêu cầu)
    public Order() {}

    // Constructor có tham số
    public Order(List<String> foodsList, double total, double tax, double delivery) {
        this.foodsList = foodsList;
        this.total = total;
        this.tax = tax;
        this.delivery = delivery;
    }

    // Getter và setter cho các thuộc tính
    public List<String> getFoodsList() {
        return foodsList;
    }

    public void setFoodsList(List<String> foodsList) {
        this.foodsList = foodsList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }
}
