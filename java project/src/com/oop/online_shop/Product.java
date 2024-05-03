package com.oop.online_shop;
import java.util.List;
import java.util.ArrayList;
public class Product extends DBConnection {
    public static String tableName = "Products";
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private float userRating;
    public Product(int id, String name, String description, double price, int quantity, float userRating){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.userRating = userRating;
    }
    public int getId(){	
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public boolean decreaseProductQuantity(int num){
        return DBConnection.decreaseProductQuantity(this.id, num);
    }


    public static List<Product> getAllProducts(){
        return getProductList();
    }

}
