package com.example.demo;

import java.security.SecureRandom;

public class PasswordGenerator {

    public static void main(String[] args) {
        int length = 20; // Longueur du mot de passe
        String password = generatePassword(length);

        System.out.println("Mot de passe généré : " + password);
    }

    public static String generatePassword(int length) {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:'\",.<>?";
        String numbers = "0123456789";

        String allChars = uppercaseChars + lowercaseChars + specialChars + numbers;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}

