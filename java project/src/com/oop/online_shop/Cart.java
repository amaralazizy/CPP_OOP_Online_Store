package com.oop.online_shop;

import java.util.ArrayList;

public class Cart extends DBConnection{
    public static String tableName = "Cart";
    private int id;
    private ArrayList<Product> products; // this will be a function not an attribute
    private int userId;

    public Cart(int id){
        this.id = id;
    }

    public double TotalPrice() {
        double totalPrice = 0.0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public boolean add_toCart(Product item, int count) {
        return addProductInCart(this.id, item.getId(), count);
    }

    public void removeCart(Product item) {
        products.remove(item);
    }

    public void remove_fromCart(Product item) {
        products.remove(item);
    }

    public void remove_fromCart(int index) {
        products.remove(index);
    }
}
