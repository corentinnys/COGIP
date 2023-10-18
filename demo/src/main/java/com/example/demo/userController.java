package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
       return "sample";

    }

    @GetMapping("/users")
    public String userPage(Model model) {
        userController userController = new userController();
        userController.getUsers();
        model.addAttribute("users", userController.getUsers());
        return "user";

    }


    @PostMapping("/users/modification")
    public String update(@RequestParam int id,@RequestParam String name,@RequestParam String role) {
        DataInsertion dataInsertion = new DataInsertion();
        dataInsertion.updateData(id, name, role);
        return "confirmation";
    }

    @PostMapping("/users/insert")
    public String submitForm(@RequestParam String name , @RequestParam String password)
    {
        System.out.println(name);


        DataInsertion dataInsertion = new DataInsertion();
        dataInsertion.insertData(name,password,"intern");
      return  "confirmation";
    }



    @GetMapping("/users/modify/{id}")
    public String userUpdatePage(@PathVariable int id, Model model) {
        User user = getUserById(id);

        if (user == null) {
            // Gérez le cas où l'utilisateur n'est pas trouvé (par exemple, renvoyez une page d'erreur)
            return "userNotFound";
        }

        model.addAttribute("user", user);
        return "userUpdate";
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


}
