package com.bridgelabz.addressbook.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDBService {
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    public AddressBookDBService() {
    }

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null) {
             addressBookDBService = new AddressBookDBService();
        }
        return addressBookDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_db?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println(connection + " connection successful");
        return connection;
    }

    public List<AddressBookData> readDate() {
        String query = "SELECT * from address_book";
        return this.getAddressBookDataUsingDB(query);
    }

    private List<AddressBookData> getAddressBookDataUsingDB(String query) {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    private List<AddressBookData> getAddressBookData(ResultSet resultSet) {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int personId = resultSet.getInt("person_id");
                int typeId= resultSet.getInt("type_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip = resultSet.getString("zip");
                String address=resultSet.getString("address");
                LocalDate date_added = resultSet.getDate("date_added").toLocalDate();
                addressBookList.add(new AddressBookData(personId,typeId,firstName,lastName,phoneNumber,email,city,state,zip,address,date_added ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    public int updateAddressBookRecord(String name, String phoneNumber) throws AddressBookException {
        String query = String.format("update address_book set phone_number = '%s' where first_name= '%s' ;", phoneNumber, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
        }
    }
    private void prepareStatementForAddressBook() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM address_book WHERE `first_name` = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AddressBookData> getAddressBookData(String first_name) {
        List<AddressBookData> addressBookDataList = null;
        if (this.addressBookDataStatement == null) {
            this.prepareStatementForAddressBook();
        }
        try {
            addressBookDataStatement.setString(1, first_name);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookDataList =this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }


    public List<AddressBookData> getEmployeePayrollForDateRange(LocalDate startDate, LocalDate endDate) {
        String query = String.format("SELECT * FROM address_book WHERE date_added BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getAddressBookDataUsingDB(query);
    }

    public Map<String, Double> getCountOfContactsByCity() {
        String query = "SELECT city,COUNT(city) as count from address_book group by city;";
        Map<String, Double> countOfContacts = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                double count = resultSet.getDouble("count");
                countOfContacts.put(city, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countOfContacts;
    }

    public AddressBookData addNewContact( String type, String first_name, String last_name, String phone_number,
                                         String email, String city, String state, String zip, String address, LocalDate localDate) {

        int person_Id = -1;
        int type_Id=-1;
        Connection connection = null;
        AddressBookData addressBookData=null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("INSERT INTO people (person_name) VALUES" +
                    "('%s')", first_name);
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                System.out.println(resultSet.toString());
                if (resultSet.next()) {
                    person_Id = resultSet.getInt(1);
                    System.out.println(person_Id+" person_Id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("Select type_id from address_type where type='%s' ; ",type);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int type_id = resultSet.getInt("type_id");
                System.out.println(type_id+" type_id");
                type_Id=type_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("INSERT INTO address_book (`person_id`,`type_id`,`first_name`,`last_name`,`phone_number`,`email`,`city`,`state`,`zip`,`address`,`date_added`) " +
                    " VALUES (%s,%s,'%s','%s','%s','%s','%s','%s','%s','%s','%s')", person_Id,type_Id,first_name,last_name,phone_number,email,city,state,zip,address,Date.valueOf(localDate));
            System.out.println(sql+" sql");
            int rowAffected = statement.executeUpdate(sql);
            if (rowAffected == 1) {
                addressBookData = new AddressBookData(person_Id, type_Id, first_name, last_name,
                        phone_number,email,city,state,zip,address,localDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return addressBookData;
    }

    public void updateMultipleContacts(AddressBookData addressBookData) {
        Map<Integer,Boolean> addStatus = new HashMap<>();
        Runnable task = ()->{
            addStatus.put(addressBookData.hashCode(),false);
            System.out.println(addressBookData.firstName+" Thread is adding");
            this.addNewContact(addressBookData.type,addressBookData.firstName,addressBookData.lastName,addressBookData.phoneNumber,addressBookData.email,addressBookData.city,
                    addressBookData.state,addressBookData.zip,addressBookData.address,addressBookData.date_added);
            System.out.println(addressBookData.firstName+" Thread is added");
            addStatus.put(addressBookData.hashCode(),true);
        };
//        System.out.println(addressBookData.firstName+" Thread is adding");
//        this.addNewContact(addressBookData.type,addressBookData.firstName,addressBookData.lastName,addressBookData.phoneNumber,addressBookData.email,addressBookData.city,
//                addressBookData.state,addressBookData.zip,addressBookData.address,addressBookData.date_added);
//        System.out.println(addressBookData.firstName+" Thread is added");
//        addStatus.put(addressBookData.hashCode(),true);
        Thread thread=new Thread(task,addressBookData.firstName);
        thread.start();
        while(addStatus.containsValue(false)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int updateContactNumber(String name, String updateNumber) {
        return 0;
    }
}
