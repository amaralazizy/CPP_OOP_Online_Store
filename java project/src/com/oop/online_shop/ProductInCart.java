package com.oop.online_shop;

public class ProductInCart {
    private Product product;
    private int count;
    public static String tableName = "ProductInCart";

    public ProductInCart(Product product, int count){
        this.product = product;
        this.count = count;
    }

    public Product getProduct(){return this.product;}
    public int getCount(){return this.count;}
}
