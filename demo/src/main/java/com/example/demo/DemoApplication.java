package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);



	}


	@GetMapping("/hello")
	public String sayHello() {
		DatabaseConnection database = new DatabaseConnection();

		// Utilisez la connexion à la base de données
		Connection connection = database.getConnection();
		String query = "SELECT * FROM user";

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				// Lire les données du résultat ici
				String userName = resultSet.getString("userName");
				System.out.println(userName);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			// Gérez l'exception ici (par exemple, en affichant un message d'erreur)
			e.printStackTrace();
		}

		// Fermez la connexion lorsque vous avez fini
		// connection.closeConnection();
		return "Hello, World!";
	}



}
