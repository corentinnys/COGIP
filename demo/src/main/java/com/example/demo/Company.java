package com.example.demo;

public class Company {

    private int id;
    private String companyName;
    private String companyCountry;
    private int companyVat;
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
    public void setCompanyVat(int companyVat)
    {
        this.companyVat = companyVat;
    }
    public void setCompanyType(String companyType)
    {
        this.companyType = companyType;
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

    public int getCompanyVat()
    {
        return  this.companyVat  ;
    }
    public String getCompanyType()
    {
        return  this.companyType ;
    }
}
