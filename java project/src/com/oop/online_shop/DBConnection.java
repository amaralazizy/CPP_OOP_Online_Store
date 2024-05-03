package com.oop.online_shop;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DBConnection {
    private static Connection con = null;

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DBConnection.con = DriverManager.getConnection(
                    "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8613256",
                    "sql8613256",
                    "hwIw3Fjflu"
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AHHHHHH");
        }
    }

    protected static ResultSet select(String tableName, String condition){
        ResultSet rs = null;
        try{
            Statement stmt = DBConnection.con.createStatement();
            rs = stmt.executeQuery("select * from " + tableName + ((condition != null) ? " WHERE " + condition : ""));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    // CART FUNCTIONS
    protected static List<ProductInCart> productsInCart(int cartId){
        List<ProductInCart> productList = new ArrayList<>();
        ResultSet productsInCartSet = select(ProductInCart.tableName, "cartId = "+cartId);
        try{
            while(productsInCartSet.next()) {
                ResultSet productSet = select(Product.tableName, "id = " + productsInCartSet.getInt("productId"));
                if(productSet.next())
                    productList.add(
                            new ProductInCart(
                                new Product(
                                        productSet.getInt("id"),
                                        productSet.getString("name"),
                                        productSet.getString("description"),
                                        productSet.getDouble("price"),
                                        productSet.getInt("quantity"),
                                        productSet.getFloat("userRating")
                                ),
                                    productsInCartSet.getInt("count")
                        )
                    );
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return productList;
    }

    protected static boolean isProductInCart(int cartId, int productId){
        try {
            return select("ProductInCart", "cartId = " + cartId + " AND " + "productId = " + productId).next();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    protected static boolean addProductInCart(int cartId, int productId, int count){
        try{
            if(!isProductInCart(cartId, productId)){
                String insertSql = "INSERT INTO " + ProductInCart.tableName + " (cartId, productId, count)" +
                        " VALUES (?, ?, ?)";

                PreparedStatement pstmt = DBConnection.con.prepareStatement(insertSql);

                pstmt.setInt(1, cartId);
                pstmt.setInt(2, productId);
                pstmt.setInt(3, count);

                int numRowsAffected = pstmt.executeUpdate();
                if(numRowsAffected > 0) return true;

            }
            else return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    protected static boolean removeFromCart(int cartId, int productId){
        try{
            PreparedStatement pstmt = DBConnection.con.prepareStatement("DELETE FROM " + ProductInCart.tableName + " WHERE cartId = ? AND productId = ?;");
            pstmt.setInt(1, cartId);
            pstmt.setInt(2, productId);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // PRODUCTS FUNCTIONS
    protected static List<Product> getProductList(){

        List<Product> productList = new ArrayList<>();
        ResultSet rs = select(Product.tableName, null);
        try{
            while(rs.next()) {
                productList.add(
                        new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                rs.getInt("quantity"),
                                rs.getFloat("userRating")
                        )
                );
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return productList;
    }
    protected static boolean decreaseProductQuantity(int productId, int num){
        try {
            // Create SQL update statement
            String updateQuery = "UPDATE " + Product.tableName +" SET quantity = quantity - ? WHERE id = ?";

            // Create prepared statement with parameters
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, ""+num);
            pstmt.setString(2, ""+productId);

            // Execute update statement
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // USERS FUNCTIONS
    protected static User authenticateUser(User user){
        User result;
        try{
            Statement stmt = DBConnection.con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select * from " + User.tableName
                            + " WHERE email='"+ user.getEmail().toLowerCase() + "' AND pass='"+user.getPass()+"'");

            if(rs.next()){
                result = new User(
                    rs.getString("name"),
                        "",
                        rs.getString("email"),
                        rs.getString("userAdress"),
                        rs.getString("creditCardNumber")
                );
                result.setId(rs.getInt("id"));
                result.setCartId(rs.getInt("cartId"));

                return result;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected static boolean createUser(User user){

        try {
            // Create Cart
            String insertSql = "INSERT INTO " + Cart.tableName + " ()" +
                    " VALUES ()";

            PreparedStatement pstmt = DBConnection.con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            int cartId = 0;

            if(pstmt.executeUpdate() > 0){
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    cartId = rs.getInt(1);
                }
            }

            if(cartId == 0) return false;

            // Create User
            insertSql = "INSERT INTO " + User.tableName + " (name, pass, email, userAdress, creditCardNumber, cartId)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";

            pstmt = DBConnection.con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPass());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getCreditCardNumber());
            pstmt.setInt(6, cartId);

            int numRowsAffected = pstmt.executeUpdate();
            if(numRowsAffected > 0){
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                return true;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    protected static Cart getCartById(int cartId){
        ResultSet cartSet = select(Cart.tableName, "id = "+cartId);
        try{
            if(cartSet.next())
                return new Cart(
                        cartSet.getInt("id")
                );
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    // PAYMENT FUNCTIONS
    protected static String savePayment(Payment payment, int cartId){
        ResultSet userSet = select(User.tableName, "id = " + payment.getUserId());
        try{
            if(userSet.next()) {
                List<ProductInCart> products = productsInCart(userSet.getInt("cartId"));
                if(!(products.size() > 0)) return "No products in cart!";

                String insertSql = "INSERT INTO " + Payment.tableName + " (value, userId)" +
                        " VALUES (?, ?)";

                PreparedStatement pstmt = DBConnection.con.prepareStatement(insertSql);

                pstmt.setFloat(1, payment.getValue());
                pstmt.setInt(2, payment.getUserId());

                int numRowsAffected = pstmt.executeUpdate();
                if (numRowsAffected > 0) {

                    pstmt = con.prepareStatement("DELETE FROM " + ProductInCart.tableName + " WHERE cartId = ?;");
                    pstmt.setInt(1, cartId);
                    pstmt.executeUpdate();

                    return "Payment processed successfully";
                }

            } else return "User does not exist!";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Unknown Error!";
    }

    public static void closeConnection() {
        try {
            DBConnection.con.close();
            //System.out.println("Connection Closed");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
