package com.amazon.jdbc1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class InsertData {
    public static void insertSampleData() {
        try (Connection connection = DatabaseConnection.connect()) {

            for (int i = 1; i <= 50; i++) {
                insertOrderData(connection, i, generateUniqueProductName(), generateRandomPrice());
            }

            for (int i = 1; i <= 50; i++) {
                insertCustomerData(connection, i, generateUniqueCustomerName(), generateRandomRegion());
            }

            for (int i = 1; i <= 50; i++) {
                insertSalesData(connection, i, i % 10 + 1, i % 5 + 1);
            }

            System.out.println("Sample data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateUniqueProductName() {
        String[] products = {"Laptop", "Smartphone", "Headphones", "Camera", "Smartwatch", "Tablet", "Printer", "Monitor"};
        return products[(int) (Math.random() * products.length)];
    }

    private static String generateUniqueCustomerName() {
        String[] firstNames = {
                "Liam", "Olivia", "Noah", "Emma", "Oliver", "Ava", "Elijah", "Sophia", "Lucas", "Isabella",
                "Mia", "Jackson", "Aiden", "Aria", "Grayson", "Amelia", "Sebastian", "Harper", "Ethan", "Evelyn"
        };
        String[] lastNames = {
                "Smith", "Johnson", "Williams", "Brown", "Jones",
                "Miller", "Davis", "García", "Rodriguez", "Martinez",
                "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
                "Thomas", "Taylor", "Moore", "Clark", "Lewis"
        };

        String firstName = firstNames[(int) (Math.random() * firstNames.length)];
        String lastName = lastNames[(int) (Math.random() * lastNames.length)];

        return firstName + " " + lastName;
    }

    private static String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }

        return result.toString();
    }

    private static double generateRandomPrice() {
        return Math.round((10.0 + Math.random() * 90.0) * 100.0) / 100.0; // Random price between 10.0 and 100.0
    }

    private static String generateRandomRegion() {
        String[] regions = {"North", "South", "East", "West", "Central"};
        return regions[(int) (Math.random() * regions.length)];
    }

    private static void insertOrderData(Connection connection, int orderId, String productName, double price) throws SQLException {
        String insertOrderDataQuery = "INSERT INTO OrderTable (orderId, productName, price) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderDataQuery)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, productName);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();
        }
    }

    private static void insertCustomerData(Connection connection, int customerId, String customerName, String region) throws SQLException {
        String insertCustomerDataQuery = "INSERT INTO CustomerTable (customerId, customerName, region) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerDataQuery)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, customerName);
            preparedStatement.setString(3, region);
            preparedStatement.executeUpdate();
        }
    }

    private static void insertSalesData(Connection connection, int orderId, int customerId, int quantity) throws SQLException {
        String insertSalesDataQuery = "INSERT INTO SalesTable (orderId, customerId, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSalesDataQuery)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
        }
    }

    public static void main(String[] args) {
        // Run this main method to insert sample data
        insertSampleData();
    }
}