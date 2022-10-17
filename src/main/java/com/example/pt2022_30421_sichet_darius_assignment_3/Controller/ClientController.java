package com.example.pt2022_30421_sichet_darius_assignment_3.Controller;

import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.ClientDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Client;
import com.example.pt2022_30421_sichet_darius_assignment_3.bll.ClientBLL;
import javafx.beans.Observable;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.pt2022_30421_sichet_darius_assignment_3.utils.ControllerUtils.renderView;

public class ClientController implements Initializable {
    public TableView<Client> clientTable;
    private ObservableList<Client> clients;


    public TableColumn idClientTable;
    public TableColumn nameClientTable;
    public TableColumn addressClientTable;
    public TableColumn emailClientTable;
    public TableColumn ageClientTable;
    public TextField idClient;
    public TextField nameClient;
    public TextField addressClient;
    public TextField emailClient;
    public TextField ageClient;
    public ClientBLL clientbll = new ClientBLL(new ClientDAO());

    public void setDetailsOfClient(Client c) {
         idClient.setText(String.valueOf(c.getId()));
         nameClient.setText(c.getName());
         addressClient.setText(c.getAddress());
         emailClient.setText(c.getEmail());
         ageClient.setText(String.valueOf(c.getAge()));
    }

    public void insertClient(ActionEvent actionEvent) {
        // idClient.getText()
        clientTable.getItems().removeAll(clients);
        clientbll.createClient(Integer.parseInt(idClient.getText()),nameClient.getText(),addressClient.getText(),emailClient.getText(),Integer.parseInt(ageClient.getText()));
        clientTable.setEditable(true);

        clients = FXCollections.observableArrayList(clientbll.getAllClients());
        clientTable.getItems().addAll(clients);
    }

    public void updateClient(ActionEvent actionEvent) {
        clientTable.getItems().removeAll(clients);
        clientbll.updateClient(Integer.parseInt(idClient.getText()),nameClient.getText(),addressClient.getText(),emailClient.getText(),Integer.parseInt(ageClient.getText()));
        clientTable.setEditable(true);

        clients = FXCollections.observableArrayList(clientbll.getAllClients());
        clientTable.getItems().addAll(clients);
    }

    public void deleteClient(ActionEvent actionEvent) throws SQLException {
        //clientTable=new TableView<>();
        //productTable.getItems().removeAll(products);
        clientTable.getItems().removeAll(clients);
clientbll.deleteClient(Integer.parseInt(idClient.getText()));
        clientTable.setEditable(true);

        clients = FXCollections.observableArrayList(clientbll.getAllClients());
        clientTable.getItems().addAll(clients);
    }

    public void backClient(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientTable.setEditable(true);
        idClientTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameClientTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressClientTable.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailClientTable.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageClientTable.setCellValueFactory(new PropertyValueFactory<>("age"));
        clients = FXCollections.observableArrayList(clientbll.getAllClients());
        clientTable.getItems().addAll(clients);
        clientTable.setVisible(true);
        clientTable.setRowFactory(clientTableView -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    Client clientSelect = row.getItem();
                    setDetailsOfClient(clientSelect);
                }
            });
            return row;
        });

    }
}
