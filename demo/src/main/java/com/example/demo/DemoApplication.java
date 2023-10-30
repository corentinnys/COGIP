package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;


@SpringBootApplication
@Controller
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}
	@GetMapping("/")
	public String home()
	{
		return "loginForm";
	}

	/*@GetMapping("/users")
	public String users() {
		userController userController = new userController(); // Assuming UserController is the correct class name

		List<User> userList = new ArrayList<>();
		for (User user : userController.getUsers()) {
			String userName = user.getUserName(); // Assuming you have a 'getUserName' method in the User class
			String userRole = user.getUserRole();
			user.setUserName(userName);
			user.setUserRole(userRole);
			userList.add(user);
		}

		try {
			*//*ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(userList);
			return json;*//*

		} catch (JsonProcessingException e) {
			// Handle exceptions related to JSON processing
			e.printStackTrace();
			return null;
		}
	}*/


}

