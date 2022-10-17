package com.example.pt2022_30421_sichet_darius_assignment_3.Controller;

import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.ClientDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.OrderDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.ProductDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Client;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Order;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Product;
import com.example.pt2022_30421_sichet_darius_assignment_3.bll.ClientBLL;
import com.example.pt2022_30421_sichet_darius_assignment_3.bll.OrderBLL;
import com.example.pt2022_30421_sichet_darius_assignment_3.bll.ProductBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;


import java.net.URL;
import java.util.ResourceBundle;

import static com.example.pt2022_30421_sichet_darius_assignment_3.utils.ControllerUtils.renderView;

public class OrderController implements Initializable {
    private ObservableList<Client> oclients;
    private ObservableList<Product> oproducts;
    public TableColumn idOrderClientTable;
    public TableColumn nameOrderClientTable;
    public TableColumn addressOrderClientTable;
    public TableColumn emailOrderClientTable;
    public TableColumn ageOrderClientTable;
    public TableColumn idOrderProductTable;
    public TableColumn nameOrderProductTable;
    public TableColumn stockOrderProductTable;
    public TableColumn priceOrderProductTable;
    public TextField orderClientID;
    public TextField orderProductID;
    public TextField orderQuantity;
    public TableView<Product> orderProductTable;
    public TableView<Client> orderClientTable;

    public ProductBLL oproductbll = new ProductBLL(new ProductDAO());
    public ClientBLL oclientbll = new ClientBLL(new ClientDAO());
    public OrderBLL orderBLL=new OrderBLL(new OrderDAO());
    private ObservableList<Order>orders;

    private int idClient;
    private String nameClient;
    private String addressClient;
    private String emailClient;
    private int ageClient;
    private int quantity;
    private int id;
    private int number;
    private String name;
    private String out="";
    private double price;
    private int cursor=0;
    public void confirmButton(ActionEvent actionEvent) throws FileNotFoundException {
        //Instantiating the File class
        File file = new File("C:\\Folder nou\\sample.txt");
        //Instantiating the PrintStream class
        PrintStream stream = new PrintStream(file);
        System.out.println("From now on "+file.getAbsolutePath()+" will be your console");
        System.setOut(stream);
        quantity=Integer.parseInt(orderQuantity.getText());
        if(quantity<=number){
    cursor++;
            number-=quantity;
            orderProductTable.getItems().removeAll(oproducts);
            oproductbll.updateClient(id,name,number,price);
            orderProductTable.setEditable(true);
            oproducts = FXCollections.observableArrayList(oproductbll.getAllProducts());
            orderProductTable.getItems().addAll(oproducts);
        out+="\n \nOrder: "+cursor +'\n'+nameClient +" "+
                addressClient+" "+emailClient+" "+ageClient+"\n"+
                name+" "+quantity+" "+price+" "+quantity*price;
            System.out.println(out);
        }

        //delete product if =stock
        //compare with stock

        //update product
        //public void updateProduct(ActionEvent actionEvent) {
        //    productTable.getItems().removeAll(products);
        //    productbll.updateClient(Integer.parseInt(idProduct.getText()),nameProduct.getText(),Integer.parseInt(stockProduct.getText()),Double.parseDouble(priceProduct.getText()));
        //    productTable.setEditable(true);
//
        //    products = FXCollections.observableArrayList(productbll.getAllProducts());
        //    productTable.getItems().addAll(products);
        //}

    }
   // public void setDetailsOfClient(Client c) {
   //     orderClientID.setText(String.valueOf(c.getId()));
   //     nameOrderClientTable.setText(c.getName());
   //     addressOrderClientTable.setText(c.getAddress());
   //     emailOrderClientTable.setText(c.getEmail());
   //     ageOrderClientTable.setText(String.valueOf(c.getAge()));
   // }
   // public void setDetailsOfProduct(Product p){
   //     idOrderClientTable.setText(String.valueOf(p.getId()));
   //     nameOrderProductTable.setText(p.getName());
   //     stockOrderProductTable.setText(String.valueOf(p.getStock()));
   //     priceOrderProductTable.setText(String.valueOf(p.getPrice()));
   //
   // }
    public void backOrder(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderClientTable.setEditable(true);
        idOrderClientTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameOrderClientTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressOrderClientTable.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailOrderClientTable.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageOrderClientTable.setCellValueFactory(new PropertyValueFactory<>("age"));
        oclients = FXCollections.observableArrayList(oclientbll.getAllClients());
        orderClientTable.getItems().addAll(oclients);
        orderClientTable.setVisible(true);
        orderClientTable.setRowFactory(clientTableView -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    Client clientSelect = row.getItem();
                    //setDetailsOfClient(clientSelect);
                    orderClientID.setText(String.valueOf(clientSelect.getId()));
                    idClient=clientSelect.getId();
                    nameClient=clientSelect.getName();
                    addressClient=clientSelect.getAddress();
                    emailClient=clientSelect.getEmail();
                    ageClient=clientSelect.getAge();

                }
            });
            return row;
        });

        orderProductTable.setEditable(true);
        idOrderProductTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameOrderProductTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockOrderProductTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceOrderProductTable.setCellValueFactory(new PropertyValueFactory<>("price"));

        oproducts = FXCollections.observableArrayList(oproductbll.getAllProducts());
        orderProductTable.getItems().addAll(oproducts);
        orderProductTable.setVisible(true);
        orderProductTable.setRowFactory(productTableView -> {
            TableRow<Product> rowp = new TableRow<>();
            rowp.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!rowp.isEmpty())) {
                    Product productSelect = rowp.getItem();
                    //setDetailsOfProduct(productSelect);
               orderProductID.setText(String.valueOf(productSelect.getId()));
               number=productSelect.getStock();
               name=productSelect.getName();
               price=productSelect.getPrice();
               id=productSelect.getId();
                }
            });
            return rowp;
        });


    }
}
