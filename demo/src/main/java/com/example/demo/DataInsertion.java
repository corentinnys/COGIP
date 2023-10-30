package com.example.demo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DataInsertion {
    public void insertData(String firstName, String password, String role) {
        Passwordhashing passwordhashing = new Passwordhashing();
        String passwordCrypte = passwordhashing.hashing(password);
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "INSERT INTO user (userName, password,role) VALUES (?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, passwordCrypte);
            statement.setString(3, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateData(int id, String newUserName, String newRole) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "UPDATE user SET userName = ?, role = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newUserName);
            statement.setString(2, newRole);
            statement.setInt(3, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Données mises à jour avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
