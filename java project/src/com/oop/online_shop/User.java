package com.oop.online_shop;

import java.util.List;

public class User extends DBConnection{
    public static String tableName = "User";
    private int id;
    private String name;
    private String pass;
    private String email;
    private String userAddress;
    private String creditCardNumber;
    private int cartId;
    
    public User() {
        // Default constructor
    }
    
    public User(String name, String pass, String email, String address, String creditCardNumber) {
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.userAddress = address;
        this.creditCardNumber = creditCardNumber;
    }
    public void setId(int id){this.id = id;}
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAddress(String address) {
        this.userAddress = address;
    }
    
    public void setCreditCardNumber(String creditCardNumber) {
    	this.creditCardNumber = creditCardNumber;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPass() {
        return this.pass;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getAddress() {
        return this.userAddress;
    }

    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }
    public int getCartId(){return this.cartId;}

    public void setCartId(int cartId){this.cartId = cartId;}

    public boolean signUp(){
        return createUser(this);
    }
    
    public User Login() {
    	return authenticateUser(this);
    }

    public Cart getUserCart(){
        return getCartById(this.cartId);
    }

    public List<ProductInCart> getProductsInCart(){
        return productsInCart(this.cartId);
    }

    public String pay(float value){
        Payment p = new Payment(value, this.id);
        return savePayment(p, cartId);
    }

    public boolean removeFromCart(int productId){
        return DBConnection.removeFromCart(this.cartId, productId);
    }

   /*  public double show_totalPrice() {
        Cart userCart = getCart();
        if (userCart != null) {
            return userCart.TotalPrice();
        }
        return 0.0;
    } */

}

