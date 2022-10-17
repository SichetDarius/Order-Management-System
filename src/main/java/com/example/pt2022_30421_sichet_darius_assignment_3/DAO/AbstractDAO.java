package com.example.pt2022_30421_sichet_darius_assignment_3.DAO;


import com.example.pt2022_30421_sichet_darius_assignment_3.Model.Order;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.pt2022_30421_sichet_darius_assignment_3.Connection.ConnectionFactory;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    private String selectAll(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        if(type.getSimpleName().equals("Order")) sb.append("`order`");
        else sb.append(type.getSimpleName());
        return sb.toString();
    }
    public  List<T> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = selectAll();
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            ConnectionFactory.close();
        }
        return null;
    }

    public T findById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close();
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }


    private String createInsertQuery(T object){
        StringBuilder sb = new StringBuilder();
        if(object instanceof Order)
            sb.append("INSERT INTO `order` ("); // i have to do this because order is a reserved keyword in mysql
        else sb.append("INSERT INTO " + type.getSimpleName() + " ( ");

        boolean firstField = true;
        for(Field field : type.getDeclaredFields()){

            {   if(!firstField) sb.append(", ");
                sb.append(field.getName());
                firstField = false;
            }
        }

        sb.append(") values (");

        firstField = true;
        for(Field field : type.getDeclaredFields()){

            {  if(!firstField) sb.append(", ");
                sb.append("?");
                firstField = false;
            }
        }
        sb.append(")");
        return sb.toString();
    }


    public void insert(T object) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(object);
        int index = 1;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(Field field : type.getDeclaredFields()){

                {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getReadMethod();
                    Object writeObject = method.invoke(object);
                    statement.setObject(index, writeObject);
                    index ++;
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: insert " + e.getMessage());
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close();
        }

    }


    //


    private String createUpdateQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(Field f : type.getDeclaredFields()){
            sb.append(f.getName() + "=?,");
        }
        sb.deleteCharAt(sb.length() - 1); // delete the last ","
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }
    //

    public void update(T object, int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery("id");
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int index = 1;
            for(Field field : type.getDeclaredFields()){
                field.setAccessible(true);
                Object value = field.get(object);
                statement.setString(index, value.toString());
                index ++;
            }
            statement.setInt(index, id);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e){
            e.printStackTrace();
        } finally{
            ConnectionFactory.close();
        }
    }

    private String createDeleteQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        if(type.getSimpleName().equals("Order")) sb.append("`order`");
        else sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();
    }

    public void deleteById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: deleteById: " + e.getMessage());
        } finally{
            ConnectionFactory.close();
        }
    }




}

