package com.example.pt2022_30421_sichet_darius_assignment_3.bll;

import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.OrderDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Client;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private OrderDAO orderDAO;

    public OrderBLL(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order findOrderById(int id) {
        Order order = null;
        try {
            order = orderDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    public void createOrder(int id, int clientID, int productID, int quantity) {
        try {
            orderDAO.insert(new Order(id, clientID, productID, quantity));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(int id, int clientID, int productID, int quantity) {
        try {
            orderDAO.update(new Order(id, clientID, productID, quantity), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {
        try {
            orderDAO.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        try {
            return orderDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
