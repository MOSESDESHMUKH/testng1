package com.amazon.jdbc1;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;

public class YourDatabaseTest {

    private static Connection connection;

    @BeforeClass
    public static void setUp() {


        // Set up database connection
        try {
            connection = DatabaseConnection.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSeleniumAndDatabaseIntegration() {

        try (Statement statement = connection.createStatement()) {
//            CreateTable.createTables();
//            InsertData.insertSampleData();
            findDuplicateRecords();
            findProductWithMaxPrice();
            


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        // Close WebDriver and database connection
//        if (driver != null) {
//            driver.quit();
//        }
//        CreateTable.dropTables();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void findDuplicateRecords() {
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {

            // Example: Find duplicate records in OrderTable based on productName
            String duplicateRecordsQuery = "SELECT productName, COUNT(*) FROM OrderTable GROUP BY productName HAVING COUNT(*) > 1";
            ResultSet resultSet = statement.executeQuery(duplicateRecordsQuery);
            System.out.println("Duplicate records in OrderTable:");
            while (resultSet.next()) {
                System.out.println("Product Name: " + resultSet.getString("productName"));
                System.out.println("Count: " + resultSet.getInt(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findProductWithMaxPrice() {
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {

            // Example: Find the product with the maximum price in OrderTable
            String maxPriceProductQuery = "SELECT * FROM OrderTable WHERE price = (SELECT MAX(price) FROM OrderTable)";
            ResultSet resultSet = statement.executeQuery(maxPriceProductQuery);

            System.out.println("Product with Max Price:");
            while (resultSet.next()) {
                System.out.println("Product Name: " + resultSet.getString("productName"));
                System.out.println("Price: " + resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}