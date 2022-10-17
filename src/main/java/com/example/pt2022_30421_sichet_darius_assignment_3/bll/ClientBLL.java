package com.example.pt2022_30421_sichet_darius_assignment_3.bll;

import com.example.pt2022_30421_sichet_darius_assignment_3.DAO.ClientDAO;
import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
private ClientDAO clientDAO;

    public ClientBLL(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Client findClientById(int id){
        Client client= null;
        try {
            client = clientDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(client==null){
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }
    public void createClient(int id, String name, String address, String email, int age){
        try {
            clientDAO.insert(new Client(id, name, address, email, age));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClient(int id, String name, String address, String email, int age){
        try {
            clientDAO.update(new Client(id, name, address, email, age), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteClient(int id) throws SQLException {
        clientDAO.deleteById(id);
    }
    public List<Client> getAllClients(){
        try {
            return clientDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // private int id;
   // private String name;
   // private String address;
   // private String email;
   // private int age;

}
