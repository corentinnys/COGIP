package com.example.demo;

import org.mindrot.jbcrypt.BCrypt;
public class Passwordhashing {

    public String hashing(String password) {
        // Générer un sel aléatoire
        String salt = BCrypt.gensalt();
        // Hasher le mot de passe avec le sel
        String hashedPassword = BCrypt.hashpw(password, salt);
        return  hashedPassword;
    }

    public boolean check(String password,String hashedPassword)
    {
        if (BCrypt.checkpw(password, hashedPassword)) {
            return true  ;
        }else {
            return false;
        }

    }
}
