package com.example.demo.controllers;

import com.example.demo.models.Company;
import com.example.demo.DatabaseConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Controller
public class companieController {

    private com.example.demo.controllers.userController userController;

    public static void main(String[] args) {
        // Vous ne pouvez pas utiliser "this" dans une méthode statique
        // Pour instancier UserController, vous pouvez faire ceci :
        //userController userController = new userController(userRepository);
    }
    @GetMapping("/company")
    public String userPage(Model model, HttpServletRequest request, HttpServletResponse response) {

        HttpSession userSession = request.getSession(false);
        if (userSession != null)
        {
            companieController companieController = new companieController();

            model.addAttribute("companies",companieController.getCompanies());

            model.addAttribute("templateName", "companies");

            return "template";

        }else
        {
            model.addAttribute("templateName", "loginForm");

            return "template";
        }


    }

    @GetMapping("company/create")
    public String create (Model model,HttpServletRequest request) {

        HttpSession userSession = request.getSession(false);
        if (userSession != null)
        {
            model.addAttribute("templateName", "createCompany");

            return "template";

        }else
        {
            model.addAttribute("templateName", "loginForm");

            return "template";

        }


    }
    @PostMapping("/company/insert")
    public String submitForm(@RequestParam String name , @RequestParam String country,@RequestParam int vat,@RequestParam String type,Model model)
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

        model.addAttribute("templateName", "confirmation");

        return "template";

    }


    @PostMapping("/company/modification")
    public String update(@RequestParam int id,@RequestParam String name,@RequestParam String country,@RequestParam int vat,@RequestParam String type,Model model)
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
        model.addAttribute("templateName", "confirmation");

        return "template";

    }


    @GetMapping("/company/delete/{id}")
    public String delete(@PathVariable int id,Model model) {
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
        model.addAttribute("templateName", "confirmation");

        return "template";
    }

    @GetMapping("/company/modify/{id}")
    public String userUpdatePage(@PathVariable int id, Model model) {
        Company company = getCompanyById(id);

        if (company == null) {
            // Gérez le cas où l'utilisateur n'est pas trouvé (par exemple, renvoyez une page d'erreur)
            model.addAttribute("templateName", "userNotFound");

            return "template";

        }

        model.addAttribute("company", company);
        model.addAttribute("templateName", "companyUpdate");

        return "template";
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
        /*String query = "SELECT company.id ,company.name,company.country,company.type,company.vat,contact.firstName\n" +
                "FROM `company` LEFT JOIN `contact` ON `company`.`id` = `contact`.`contact_company_id`";
*/

        String query ="SELECT  * from company";
        List<Company> companyList = new ArrayList<>();


        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Lire les données du résultat ici
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                String type = resultSet.getString("type");
                int vat = resultSet.getInt("vat");
                int id = resultSet.getInt("id");

                Company company = new Company();
                company.setCompanyName(name);
                company.setCompanyCountry(country);
                company.setCompanyVat(vat);
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




    @GetMapping("/company/tri/{type}")
    public String tri(@PathVariable String type, Model model) {
        List<Company> companyList = new ArrayList<>();
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * FROM company WHERE type = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, type);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int vat = resultSet.getInt("vat");
                String companyType = resultSet.getString("type");
                int id = resultSet.getInt("id");

                Company company = new Company();
                company.setCompanyName(name);
                company.setCompanyCountry(country);
                company.setCompanyVat(vat);
                company.setCompanyType(companyType);
                company.setCompanyId(id);

                companyList.add(company);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            // Handle the exception properly in your application
            e.printStackTrace();
        } finally {
            // Close the connection in a finally block to ensure it's always closed
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Handle any closing exceptions properly
                    e.printStackTrace();
                }
            }
        }

        // Add the companyList to the model so it can be used in your view
        model.addAttribute("companies", companyList);

        model.addAttribute("templateName", "companyTri");

        return "template";
        // Return the name of the view to be rendered, for example, "companyList" or whatever your view name is.

    }



}


