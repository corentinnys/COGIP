package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

@Controller
@RequestMapping("/user")
public class loginController {

    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password, Model model) {
        try {
            DatabaseConnection database = new DatabaseConnection();
            Connection connection = database.getConnection();
            String query = "SELECT * FROM user WHERE userName = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                model.addAttribute("user", resultSet.getString("userName"));
                model.addAttribute("role", resultSet.getString("role"));
                return "loginSuccessful"; // Use an appropriate view name for a successful login.
            } else {
                return "loginFailed"; // Use an appropriate view name for a failed login.
            }
        } catch (SQLException e) {
            // Handle the exception or log it.
            e.printStackTrace();
            return "errorPage"; // Create an error page for handling database errors.
        }
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm"; // Return the name of the HTML template for the login form.
    }
}







