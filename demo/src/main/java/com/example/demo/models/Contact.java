package com.example.demo.models;

public class Contact {

    private int id;

    private  String firstName;
    private  String lastName;
    private  String phone;
    private  String email;
    private  int contact_company_id;
    private  String company;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContact_company_id(int contact_company_id) {
        this.contact_company_id = contact_company_id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getContact_company_id() {
        return contact_company_id;
    }
}
