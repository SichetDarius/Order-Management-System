package com.example.pt2022_30421_sichet_darius_assignment_3.Controller;

import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.ProductDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Client;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Product;
import com.example.pt2022_30421_sichet_darius_assignment_3.bll.ClientBLL;
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

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.pt2022_30421_sichet_darius_assignment_3.utils.ControllerUtils.renderView;

public class ProductController implements Initializable {
    public TableColumn idProductTable;
    public TableColumn nameProductTable;
    public TableColumn stockProductTable;
    public TableColumn priceProductTable;
    public TextField idProduct;
    public TextField nameProduct;
    public TextField stockProduct;
    public TextField priceProduct;
    public TableView<Product> productTable;
    private ObservableList<Product> products;
    public ProductBLL productbll = new ProductBLL(new ProductDAO());
    public void setDetailsOfProduct(Product p){
       idProduct.setText(String.valueOf(p.getId()));
       nameProduct.setText(p.getName());
       stockProduct.setText(String.valueOf(p.getStock()));
       priceProduct.setText(String.valueOf(p.getPrice()));

    }
    public void insertProduct(ActionEvent actionEvent) {
        productTable.getItems().removeAll(products);
        productbll.createProduct(Integer.parseInt(idProduct.getText()),nameProduct.getText(),Integer.parseInt(stockProduct.getText()),Double.parseDouble(priceProduct.getText()));
        productTable.setEditable(true);
        products = FXCollections.observableArrayList(productbll.getAllProducts());
        productTable.getItems().addAll(products);
    }

    public void deleteProduct(ActionEvent actionEvent) {
        productTable.getItems().removeAll(products);
        productbll.deleteProduct(Integer.parseInt(idProduct.getText()));
        productTable.setEditable(true);

        products = FXCollections.observableArrayList(productbll.getAllProducts());
        productTable.getItems().addAll(products);
    }

    public void updateProduct(ActionEvent actionEvent) {
        productTable.getItems().removeAll(products);
        productbll.updateClient(Integer.parseInt(idProduct.getText()),nameProduct.getText(),Integer.parseInt(stockProduct.getText()),Double.parseDouble(priceProduct.getText()));
        productTable.setEditable(true);

        products = FXCollections.observableArrayList(productbll.getAllProducts());
        productTable.getItems().addAll(products);
    }
//bll datele din baza de date
    //introduc bll in tabel
    //manevre cu text field
    public void backProduct(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "hello-view.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTable.setEditable(true);
        idProductTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProductTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockProductTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceProductTable.setCellValueFactory(new PropertyValueFactory<>("price"));

        products = FXCollections.observableArrayList(productbll.getAllProducts());
        productTable.getItems().addAll(products);
        productTable.setVisible(true);
        productTable.setRowFactory(productTableView -> {
            TableRow<Product> rowp = new TableRow<>();
            rowp.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!rowp.isEmpty())) {
                    Product productSelect = rowp.getItem();
                    setDetailsOfProduct(productSelect);

                }
            });
            return rowp;
        });

    }
}
