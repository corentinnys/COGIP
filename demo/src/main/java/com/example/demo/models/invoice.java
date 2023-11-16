package com.example.demo.models;

public class invoice {

    private int id;

    private  int invoiceCompanyID;

    private  int invoiceContactID;
    private String company;

    private String contactLastName;

    private String contacFirstName;
    public void setId(int id) {
        this.id = id;
    }

    public void setInvoiceCompanyID(int invoiceCompanyID) {
        this.invoiceCompanyID = invoiceCompanyID;
    }

    public void setInvoiceContactID(int invoiceContactID) {
        this.invoiceContactID = invoiceContactID;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public void setContactFirstName(String contacFirstName) {
        this.contacFirstName = contacFirstName;
    }

    public int getId() {
        return id;
    }

    public int getInvoiceCompanyID() {
        return invoiceCompanyID;
    }

    public int getInvoiceContactID() {
        return invoiceContactID;
    }

    public String getCompany() {
        return company;
    }
    public  String getContactLastName()
    {
        return contactLastName;
    }

    public String getContactFirstName() {
        return contacFirstName;
    }
}