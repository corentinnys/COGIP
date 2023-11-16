package com.example.demo.controllers;

import com.example.demo.DataInsertion;
import com.example.demo.DatabaseConnection;
import com.example.demo.PasswordGenerator;
import com.example.demo.SendEmail;
import com.example.demo.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
@Controller
public class userController {

    public List<User> getUsers() {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * FROM user";

        List<User> userList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Lire les données du résultat ici
                String userName = resultSet.getString("userName");
                String role = resultSet.getString("role");
                int id = resultSet.getInt("id");

                User user = new User();
                user.setUserName(userName);
                user.setUserRole(role);
                user.setUserId(id);

                userList.add(user);


            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            // Gérez l'exception ici (par exemple, en affichant un message d'erreur)
            e.printStackTrace();
        } finally {
            //database.closeConnection(); // Assurez-vous de fermer la connexion dans une clause finally.
        }

        return userList;
    }

    @GetMapping("/sample")
    public String samplePage(Model model) {
        model.addAttribute("message", "Hello, World!");
        model.addAttribute("templateName", "sample");
        return "template";

    }



    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable int id,Model model) {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "DELETE FROM user WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate(); // Exécutez la requête pour effectuer la suppression
        } catch (SQLException e) {
            // Gérez l'exception ici (par exemple, en affichant un message d'erreur)
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("templateName", "confirmation");
        return "template";

    }


    @GetMapping("/users")
    public String userPage(Model model, HttpServletRequest request) {
        HttpSession userSession = request.getSession(false);

        if (userSession != null) {

            userController userController = new userController();
            model.addAttribute("users", userController.getUsers());
            model.addAttribute("templateName", "user");
            return "template";

        }else
        {
            model.addAttribute("templateName", "loginFrom");
            return "template";
        }

    }


    @PostMapping("/users/modification")
    public String update(@RequestParam int id,@RequestParam String name,@RequestParam String role,Model model) {
        DataInsertion dataInsertion = new DataInsertion();
        dataInsertion.updateData(id, name, role);
        model.addAttribute("templateName", "confirmation");
        return "template";
    }

    @PostMapping("/users/insert")
    public String submitForm(@RequestParam String name,Model model)
    {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password2 = passwordGenerator.generatePassword(12);
        System.out.println(password2);
        SendEmail sendEmail = new SendEmail();
        sendEmail.sendEmail(password2);
        DataInsertion dataInsertion = new DataInsertion();
        dataInsertion.insertData(name,password2,"intern");

        model.addAttribute("templateName", "confirmation");
        return "template";


    }



    @GetMapping("/users/modify/{id}")
    public String userUpdatePage(@PathVariable int id, Model model) {
        User user = getUserById(id);

        if (user == null) {
            // Gérez le cas où l'utilisateur n'est pas trouvé (par exemple, renvoyez une page d'erreur)
            model.addAttribute("templateName", "userNotFound");
            return "template";
        }

        model.addAttribute("user", user);
        model.addAttribute("templateName", "userUpdate");
        return "template";
    }




    private User getUserById(int id) {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * FROM user WHERE id = ?";
        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Si l'utilisateur est trouvé, créez un objet User avec les données de la base de données
                user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setUserRole(resultSet.getString("role"));

                // Ajoutez d'autres attributs comme nécessaire
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // Gérez l'exception ici (par exemple, en affichant un message d'erreur)
            e.printStackTrace();
        }

        return user;
    }


    public boolean checkIfConnect(HttpServletRequest request)
    {
        HttpSession userSession = request.getSession(false);
        if (userSession != null)
        {
            return true;
        }else
        {
            return false;
        }

    }




}
