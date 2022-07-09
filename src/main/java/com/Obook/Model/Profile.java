package com.Obook.Model;


import java.sql.*;
import java.util.ArrayList;


public class Profile{
    private String username;
    private String password;
    private String email;

    public Profile(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}