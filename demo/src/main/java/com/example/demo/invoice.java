package com.example.demo;

public class invoice {

    private int id;

    private  int invoiceCompanyID;

    private  int invoiceContactID;

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoiceCompanyID(int invoiceCompanyID) {
        this.invoiceCompanyID = invoiceCompanyID;
    }

    public void setInvoiceContactID(int invoiceContactID) {
        this.invoiceContactID = invoiceContactID;
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
}