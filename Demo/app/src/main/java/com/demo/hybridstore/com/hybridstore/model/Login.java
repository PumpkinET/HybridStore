package com.demo.hybridstore.com.hybridstore.model;

public class Login {

    private String email;
    private String password;
    private String avatar;
    private String name;
    private String fullname;
    private String streetAdd;
    private String country;
    private String city;
    private String postalCode;
    private String phonenumber;
    private String session;

    public Login(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public Login(String email, String password, String avatar, String name, String fullname, String streetAdd, String country, String city, String postalCode, String phonenumber, String session) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.name = name;
        this.fullname = fullname;
        this.streetAdd = streetAdd;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.phonenumber = phonenumber;
        this.session = session;
    }

    public Login(String email, String password, String avatar, String name, String fullname, String streetAdd, String country, String city, String postalCode, String phonenumber) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.name = name;
        this.fullname = fullname;
        this.streetAdd = streetAdd;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getSession() {
        return session;
    }

    public String getFullname() {
        return fullname;
    }

    public String getStreetAdd() {
        return streetAdd;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
