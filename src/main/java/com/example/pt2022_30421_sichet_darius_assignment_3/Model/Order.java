package com.example.pt2022_30421_sichet_darius_assignment_3.Model;

public class Order {
   private int id;
   private int ClientID;
   private int productID;
   private int quantity;
    public Order(){}
    public Order(int id, int clientID, int productID, int quantity) {
        this.id = id;
        ClientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
