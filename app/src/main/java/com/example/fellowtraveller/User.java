package com.example.fellowtraveller;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;

    public User(int id, String name, String email,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public User(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



    public String getPassword() {
        return password;
    }
}
