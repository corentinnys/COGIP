package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
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

    @GetMapping("company/create")
    public String create (Model model) {

        return "createCompany";
    }
    @PostMapping("/company/insert")
    public String submitForm(@RequestParam String name , @RequestParam String country,@RequestParam int vat,@RequestParam String type)
    {

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "INSERT INTO company (name, country,vat,type,timestamp) VALUES (?, ?,? ,?,current_timestamp)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, country);
            statement.setInt(3, vat);
            statement.setString(4, type);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  "confirmation";
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
