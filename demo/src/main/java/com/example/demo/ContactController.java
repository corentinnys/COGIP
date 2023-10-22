package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    public List<Contact> getContacts() {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * from contact";

        List<Contact> contactList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                // Créez un objet Contact en utilisant les données extraites.
                Contact contact = new Contact(); // Vous devez créer un constructeur approprié pour la classe Contact.
                contact.setLastName(lastName);
                contact.setFirstName(firstName);
                contact.setId(id);
                contactList.add(contact);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Assurez-vous de fermer la connexion dans une clause finally.
           // database.closeConnection();
        }

        return contactList;
    }

    @PostMapping("/contact/update")
    public String updateContact(Model model,@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,@RequestParam String phone,@RequestParam String email)
    {
        System.out.print("passe");
        this.updateData(id,firstName,lastName,phone,email);

        return "redirect:/contacts";
    }

    @GetMapping("/contacts/delete/{id}")
    public String delete(@PathVariable int id) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "DELETE FROM contact WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/contacts";
    }


    @GetMapping("/contacts")
    public String userPage(Model model, HttpServletRequest request) {
        HttpSession userSession = request.getSession(false);

        if (userSession != null) {
            ContactController contactController = new ContactController();

            model.addAttribute("contacts",contactController.getContacts());

            return "contact";
        }else
        {
            return "loginForm";
        }

    }

    @GetMapping("/contacts/modify/{id}")
    public String ContactForm(@PathVariable int id, Model model) {

        Contact contact = getContactById(id);

        if (contact == null) {
            // Gérez le cas où l'utilisateur n'est pas trouvé (par exemple, renvoyez une page d'erreur)
            return "contactNotFound";
        }

        model.addAttribute("contact", contact);
        return "contactUpdate";
    }

@GetMapping("contact/new")
    public String create()
{
    return "createFormContact";
}


@PostMapping("contact/add")
public String add(@RequestParam String firstName , @RequestParam String lastName,@RequestParam String phone, @RequestParam String email){
    try (Connection connection = new DatabaseConnection().getConnection()) {
        String sql = "INSERT INTO contact (firstName, lastName,phone,email,contact_company_id) VALUES (?, ?,?,?,1)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, phone);
        statement.setString(4, email);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Data inserted successfully.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "redirect:/contacts";
            //return "contact";
}

    private Contact getContactById(int id) {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT * FROM contact WHERE id = ?";
        Contact contact = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Si le contact est trouvé, créez un objet Contact avec les données de la base de données
                contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setFirstName(resultSet.getString("firstName"));
                contact.setLastName(resultSet.getString("lastName"));
                contact.setEmail(resultSet.getString("email"));
                contact.setPhone(resultSet.getString("phone"));
                // Ajoutez d'autres attributs comme nécessaire
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // Gérez l'exception ici (par exemple, en affichant un message d'erreur)
            e.printStackTrace();
        }

        return contact;
    }


    public void updateData(int id,String firstName, String lastName,String phone, String email) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "UPDATE contact SET firstName = ?, lastName = ?, phone= ?, email=? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setInt(5, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Données mises à jour avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

