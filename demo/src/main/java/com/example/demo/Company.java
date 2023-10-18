package com.example.demo;

public class Company {

    private int id;
    private String companyName;
    private String companyCountry;
    private String companyType;


    public void setCompanyId(int id)
    {
        this.id = id;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    public void setCompanyCountry(String companyCountry)
    {
        this.companyCountry = companyCountry;
    }

    public Integer getId()
    {
        return this.id;
    }
    public String getCompanyName()
    {
       return  this.companyName ;
    }
    public String getCompanyCountry()
    {
        return  this.companyCountry ;
    }
}
