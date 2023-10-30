package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpSession;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class loginController {

    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password, Model model,HttpServletRequest request, HttpServletResponse response) {
        try {
            DatabaseConnection database = new DatabaseConnection();
            Connection connection = database.getConnection();
            String query = "SELECT * FROM user WHERE userName = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Passwordhashing passwordhashing = new Passwordhashing();
                boolean checkingPassword =passwordhashing.check(password,resultSet.getString("password"));
                if (checkingPassword== true)
                {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", resultSet.getString("userName"));
                    session.setAttribute("role", resultSet.getString("role"));

                    model.addAttribute("user", resultSet.getString("userName"));
                    //model.addAttribute("role", resultSet.getString("role"));
                    return "loginSuccessful";
                }else {
                    return "loginFailed";
                }


            } else {
                return "loginFailed";
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



        @GetMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.invalidate();
        return "loginForm";
    }


    @GetMapping("/register")
    public String registerForm(){
        return "registerForm";
    }



}







