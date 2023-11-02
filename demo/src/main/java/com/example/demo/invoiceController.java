package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class invoiceController {



    public List<invoice> getInvoiceWithCompany() {
        DatabaseConnection database = new DatabaseConnection();
        Connection connection = database.getConnection();
        String query = "SELECT i.id, i.invoice_company_id, i.invoice_contact_id, c.name,ctn.firstName,ctn.lastName \n" +
                "FROM invoice i INNER JOIN company c ON i.invoice_company_id = c.id\n" +
                "INNER JOIN contact ctn on i.invoice_contact_id = ctn.id";

        List<invoice> invoicesList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int companyID = resultSet.getInt("invoice_company_id");
                int contactID = resultSet.getInt("invoice_contact_id");
                String companyName = resultSet.getString("name");
                String contactFirstName = resultSet.getString("firstName");
                String contactLastName = resultSet.getString("lastName");

                invoice invoice = new invoice();
                invoice.setId(id);
                invoice.setInvoiceCompanyID(companyID);
                invoice.setInvoiceContactID(contactID);

                Company company = new Company();

                company.setCompanyName(companyName);
                Contact contact = new Contact();
                contact.setFirstName(contactFirstName);
                contact.setLastName(contactLastName);

                invoice.setContactLastName(contactLastName);
                invoice.setContactFirstName(contactFirstName);
                invoice.setCompany(companyName);
                invoicesList.add(invoice);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Assurez-vous de fermer la connexion dans une clause finally.
            // database.closeConnection();
        }

        return invoicesList;
    }





    @GetMapping("/invoice")
    public String index(Model model, HttpServletRequest request) {
        HttpSession userSession = request.getSession(false);

        if (userSession != null) {
            // Vous devriez créer une instance du contrôleur approprié, pas de la classe Invoice
            invoiceController invoiceController = new invoiceController();

            List<invoice> invoices = invoiceController.getInvoiceWithCompany();
            System.out.println(invoiceController.getInvoiceWithCompany());
            model.addAttribute("invoices", invoices);
            model.addAttribute("templateName", "invoice");

            return "template";

        } else {
            model.addAttribute("templateName", "loginForm");

            return "template";

        }
    }


}
