package com.example.foodapp.Domain;

public class Hotdog {
    private String name;
    private int time;
    private double price;
    private double rating;
    private int imageResourceId;
    // private int imageResourceId; // Nếu bạn có sử dụng hình ảnh từ drawable

    public Hotdog(String name, int time, double price, double rating, int imageResourceId) {
        this.name = name;
        this.time = time;
        this.price = price;
        this.rating = rating;
        this.imageResourceId = imageResourceId;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    // public int getImageResourceId() {
    //     return imageResourceId;
    // }
}