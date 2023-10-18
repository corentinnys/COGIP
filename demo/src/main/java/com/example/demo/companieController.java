package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class companieController {

    @GetMapping("/company")
    public String userPage(Model model) {
        companieController companieController = new companieController();
        //companieController.getCompanies();
        model.addAttribute("companies",companieController.getCompanies());
        return "companies";
    }


    public List<Company> getCompanies() {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * FROM company";

        List<Company> companyList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Lire les données du résultat ici
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                String type = resultSet.getString("type");
                int id = resultSet.getInt("id");

                Company company = new Company();
                company.setCompanyName(name);
                company.setCompanyCountry(country);
                company.setCompanyId(id);

                companyList.add(company);


            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            // Gérez l'exception ici (par exemple, en affichant un message d'erreur)
            e.printStackTrace();
        } finally {
            //database.closeConnection(); // Assurez-vous de fermer la connexion dans une clause finally.
        }

        return companyList;
    }

}
