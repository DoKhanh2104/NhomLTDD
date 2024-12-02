package com.example.foodapp.Domain;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private ArrayList<Foods> foods;
    private double totalPrice;
    private long timestamp;
    private String status;

    public void calculateTotalPrice(ArrayList<Foods> orderedFoods) {
        double total = 0;
        for (Foods food : orderedFoods) {
            total += food.getPrice() * food.getNumberInCart();
        }
        // Lưu tổng vào totalPrice
        this.totalPrice = Math.floor(total) ;
    }
    // Constructor mặc định
    public Order() {
    }

    // Constructor với các tham số
    public Order(String orderId, ArrayList<Foods> foods, double totalPrice, long timestamp, String status) {
        this.orderId = orderId;
        this.foods = foods;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
        this.status = status;
    }

    // Getter và Setter cho các thuộc tính
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Foods> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Foods> foods) {
        this.foods = foods;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
