package com.example.demo;

public class User {

    private int id;
    private String userName;
    private String role;


    public void setUserId(int id)
    {
        this.id = id;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public void setUserRole(String role)
    {
        this.role = role;
    }

    public String getUserName()
    {
        return this.userName;
    }
    public String getUserRole()
    {
        return this.role;
    }

    public Integer getId()
    {
        return this.id;
    }


}
