package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class companieController {

    @GetMapping("/company")
    public String userPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession userSession = request.getSession(false);
        if (userSession != null)
        {
            companieController companieController = new companieController();
            //companieController.getCompanies();
            model.addAttribute("companies",companieController.getCompanies());
            return "companies";
        }else
        {
            return "loginForm";
        }


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


    @PostMapping("/company/modification")
    public String update(@RequestParam int id,@RequestParam String name,@RequestParam String country,@RequestParam int vat,@RequestParam String type)
    {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "UPDATE company SET name = ?, country = ?, vat = ?, type = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, country);
            statement.setInt(3, vat);
            statement.setString(4, type);
            statement.setInt(5, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Données mises à jour avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "confirmation";
    }


    @GetMapping("/company/delete/{id}")
    public String delete(@PathVariable int id) {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "DELETE FROM company WHERE id = ?";

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

        return "confirmation"; // Vous pouvez rediriger vers une page de confirmation ou toute autre page appropriée.
    }

    @GetMapping("/company/modify/{id}")
    public String userUpdatePage(@PathVariable int id, Model model) {
        Company company = getCompanyById(id);

        if (company == null) {
            // Gérez le cas où l'utilisateur n'est pas trouvé (par exemple, renvoyez une page d'erreur)
            return "userNotFound";
        }

        model.addAttribute("company", company);

        return "companyUpdate";
    }



    private Company getCompanyById(int id) {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * FROM company WHERE id = ?";
        Company company = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Si l'utilisateur est trouvé, créez un objet User avec les données de la base de données
                company = new Company();
                company.setCompanyId(resultSet.getInt("id"));
                company.setCompanyName(resultSet.getString("name"));
                company.setCompanyCountry(resultSet.getString("country"));
                company.setCompanyVat(resultSet.getInt("vat"));
                company.setCompanyType(resultSet.getString("type"));


                // Ajoutez d'autres attributs comme nécessaire
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // Gérez l'exception ici (par exemple, en affichant un message d'erreur)
            e.printStackTrace();
        }

        return company ;
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
