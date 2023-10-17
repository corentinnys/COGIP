package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;


public class DatabaseConnection {
    public Connection getConnection() {
        // Informations de connexion à la base de données
        String url = "jdbc:mysql://188.166.24.55:3306/hamilton-8-cogip-co";
        String utilisateur = "cogip-co-admin";
        String motDePasse = "elDKLJvBcoBby3h3";


        Connection connexion = null;

        try {
            // Chargement du driver JDBC (assurez-vous d'avoir le pilote JDBC approprié dans votre projet)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion à la base de données
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            if (connexion != null) {
                System.out.println("Connexion à la base de données réussie !");
                return connexion;
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Le driver JDBC n'a pas pu être chargé.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données.");
            e.printStackTrace();
        }
        return null;
    }
}
