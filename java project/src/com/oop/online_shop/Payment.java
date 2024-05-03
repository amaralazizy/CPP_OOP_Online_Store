package com.oop.online_shop;

public class Payment extends DBConnection{
    public static String tableName = "Payment";
    private int id;
    private float value;
    private int userId;

    public Payment(float value, int userId){
        this.value = value;
        this.userId = userId;
    }
    
    public void process_payment(String creditCardNumber) {
        
    }
    
    public void apply_discount(String discountCode) {
        
    }

    public float getValue(){return value;}
    public int getUserId(){return userId;}

}

