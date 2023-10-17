package com.example.demo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DataInsertion {
    public void insertData(String firstName, String password, String role) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "INSERT INTO user (userName, password,role) VALUES (?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, password);
            statement.setString(3, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
