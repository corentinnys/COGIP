package com.example.demo.models;

import javax.persistence.*;



@Entity // for specifies class is an entity and is mapped to a database table.

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String role;


    public void setUserId(long id)
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

    public Long getId()
    {
        return this.id;
    }


}
