package com.example.pt2022_30421_sichet_darius_assignment_3.bll;

import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.ProductDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private final ProductDAO productDAO;

    public ProductBLL(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    public Product findProductById(int id){
        Product product= null;
        try {
            product = productDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(product==null){
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }
    public void createProduct(int id, String name, int stock, double price){
        try {
            productDAO.insert(new Product(id, name, stock, price));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateClient(int id, String name, int stock, double price){
        try {
            productDAO.update(new Product(id, name, stock, price),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteProduct(int id){
        try {
            productDAO.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Product> getAllProducts(){
        try {
            return productDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
