package com.Obook;

import com.Obook.Model.Profile;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Obook.BooksAct.booksList;
import static com.Obook.Internal.filepath;

public class userAuth {
    private static FileWriter file;
    public static Scanner scan = new Scanner(System.in);
    public static Profile self;

    public void userLogin(){

        System.out.println("===>Welcome to OBook<===\n");
        System.out.println("press R to register");
        System.out.println("Register\n");

        System.out.println("username: ");
        String user = scan.nextLine();

        System.out.println("password: ");
        String pass = scan.nextLine();

        if(pass.equals("R") || pass.equals("r") || user.equals("r") || user.equals("R")){
            userRegister();
        }
        else if (!authentication(user, "", pass, false)) {
            System.out.println("username or password is wrong");
            userLogin();
        }
        else{
            System.out.println("login successful");
            setLogin(user, checkAccount(user).getEmail(), pass);
        }
    }

    public void userRegister() {
        System.out.println("===>Register<===");
        System.out.println("username: ");
        String user = scan.nextLine();

        System.out.println("Email: ");
        String email = scan.nextLine();

        System.out.println("password: ");
        String pass = scan.nextLine();
        if (authentication(user, email, pass, true)) {
            setRegister(user, email , pass);
            userLogin();
        }else{
            String report = !authentication(user, email, pass, true) ?
                    "username or password is not correct": "true";
            System.out.println(report);
            userRegister();
        }
    }

    //Account Creation and Authentication
    public void setLogin(String user, String email, String pass){
        self = new Profile(user, email, pass);
    }


    public void setRegister (String username, String email, String password){
        try {
            Gson gson = new Gson();
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(filepath + username +".json" );
            gson.toJson(new Profile(username, email,  password), file);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout(String username) {
        self = new Profile(null, null, null);
        booksList.clear();
    }

    public Profile checkAccount(String name){
        try {
            Gson gson = new Gson();

            Profile loadProfile = gson.fromJson(new FileReader( filepath + name +".json"), Profile.class);
            return loadProfile;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean authentication(String username, String email, String password, boolean registerMode){

        String regexPass =  "^(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        String regexEmail = "^(.+)@(.+)$";
        Pattern patternemail = Pattern.compile(regexEmail);
        Pattern patternpass = Pattern.compile(regexPass);
        Matcher matcheremail = patternemail.matcher(email);
        Matcher matcherpass = patternpass.matcher(password);
        boolean loginMode = !registerMode ? true : false;
        if(
                registerMode &&
                matcheremail.matches() &&
                matcherpass.matches() &&
                checkAccount(username) == null
        ){
            return true;
        }else if(checkAccount(username) == null){
            return false;
        }
        else if
        (
                loginMode &&
                checkAccount(username).getUsername().equals(username) &&
                checkAccount(username).getPassword().equals(password)
        ){
            return true;
        }else {
            return false;
        }

    }

}
