package com.Obook.Controller;

import com.Obook.EmailSender;
import com.Obook.Model.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Obook.BooksAct.booksAct;
import static com.Obook.BooksAct.booksList;
import static com.Obook.Internal.filepath;
import static com.Obook.userAuth.self;

@Controller
public class AccountController {
    private static FileWriter file;
    public static AccountController accountController;
    public static ArrayList<Profile> accounts = new ArrayList<>();
    BookController bookController = new BookController();
    BookControllerAdmin bookControllerAdmin = new BookControllerAdmin();

    @Autowired
    private EmailSender senderService;

    public AccountController(){
        accounts = reloadAccountsJson();
    }

    public void sendVerification(String toEmail, String subject, String body) {
        senderService.sendMail(toEmail, subject, body);
    }

    @RequestMapping("/loginpage")
    String login (){
        return "Login";
    }

    @PostMapping("/loginpage/login")
    String login (@RequestParam String username, @RequestParam String password, Model model) throws NoSuchAlgorithmException {
        if (username.equals("Obook") && password.equals("Obook1906")) {
            authentication(username, "", password, false);
            bookControllerAdmin.getProducts(model);
            return "adminPage";
        }
        else if (!authentication(username, "", password, false)) {
            System.out.println("username or password is wrong");
            return "login";
        }

        booksList = booksAct.reloadbuyerJson(self.getUsername());
        bookController.getProducts(model);
        return "MainPage";
    }

    @RequestMapping("/registerpage")
    String register (){
        return "register";
    }

    @PostMapping("/verficationMail")
    String verification (@RequestParam String username,@RequestParam String email, @RequestParam String password, Model model){
        if (authentication(username, email, password, true)) {
            sendVerification(email, "from Obook",
                    "Thanks for signing up!\n" +
                    "Your account has been created, you can login with the following credentials after you have activated your account by pressing the url below.\n" +
                    "  \n" +
                    "------------------------\n" +
                    "Username: "+username+"\n" +
                    "Email: "+email+"\n" +
                    "------------------------\n" +
                    "  \n" +
                    "Please click this link to activate your account:\n" +
                    "http://localhost:8080/registerpage/register?username="+username+"&email="+email+"&password="+password);
            return "login";
        }
        return "register";
    }

    @GetMapping("/registerpage/register")
    String register (@RequestParam String username,@RequestParam String email, @RequestParam String password, Model model){
        setRegister(username, email, password); ;
        return "Login";
    }

    @GetMapping("/logout")
    String logout (){
        if (self != null) {
            logout(self.getUsername());
        }
        return "Login";
    }

    //Account Creation and Authentication
    public void setLogin(String user, String email, String pass){
        self = new Profile(user, email, pass);
    }

    public void setRegister (String username, String email, String password){
        accounts.add(new Profile(username, email,  password));
        saveaccountJson();
    }

    public void logout(String username) {
        self = new Profile(null, null, null);
        booksList.clear();
    }

    public boolean authentication(String username, String email, String password, boolean registerMode) throws NoSuchAlgorithmException {

        String regexPass =  "^(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        String regexEmail = "^(.+)@(.+)$";
        Pattern patternemail = Pattern.compile(regexEmail);
        Pattern patternpass = Pattern.compile(regexPass);
        Matcher matcheremail = patternemail.matcher(email);
        Matcher matcherpass = patternpass.matcher(password);
        boolean loginMode = !registerMode ? true : false;

        if (
                    registerMode &&
                            matcheremail.matches() &&
                            matcherpass.matches()
            ) {
                for (Profile pr : accounts) {
                    if (pr.getEmail().equals(email) || pr.getUsername().equals(username)) {
                        System.out.println("already use email or username");
                        return false;
                    }
                }
                return true;
            } else if
            (
                    loginMode
            ) {
                for (Profile pr : accounts) {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    byte[] bytes = md.digest();
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < bytes.length; i++){
                        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    if (pr.getUsername().equals(username) && pr.getPassword().equals(password)) {
                        setLogin(username, pr.getEmail(), password);
                        return true;
                    }
                }
                return false;
            }
            return false;

    }
    //databaseJson
    public ArrayList<Profile> reloadAccountsJson(){
        try {
            Gson gson = new Gson();
            System.out.println("load profile");
            ArrayList<Profile> loadProfile = gson.fromJson(new FileReader(filepath + "Account.json"), new TypeToken<ArrayList<Profile>>() {}.getType());
            return loadProfile;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("something's wrong with the accounts");
            return null;
        }
    }

    public void saveaccountJson() {
        try {
            Gson gson = new Gson();
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(filepath + "Account.json");
            gson.toJson(accounts, file);

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

}

