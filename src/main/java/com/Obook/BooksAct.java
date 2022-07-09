package com.Obook;

import com.Obook.Model.Books;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.Obook.Internal.filepath;

public class BooksAct {
    private static FileWriter file;
    public static ArrayList<Books> booksList = new ArrayList<>();
    public static ArrayList<Books> booksStore = new ArrayList<>();
    public static BooksAct booksAct = new BooksAct();
    Scanner scan = new Scanner(System.in);

    public BooksAct(){
        booksStore = reloadstoreJson();
        System.out.println(booksStore.size());
    }

    //Books CRUD
    public void showBooks(){
        if(booksStore.size() <= 0) {
            System.out.println("no books");
        }else{
            System.out.println("--List Book--");
            for(int i = 0; i < booksStore.size(); i++) {
                System.out.println( i+1 + ". Title: " +
                        booksStore.get(i).getTitle() + "\n   Price: " +
                        booksStore.get(i).getPrice() + "\n   Categories: " +
                        booksStore.get(i).getCategories()  + "\n   Page Count: " +
                        booksStore.get(i).getPageCount() + "\n   Authors: " +
                        booksStore.get(i).getAuthors() + "\n   ISBN: " +
                        booksStore.get(i).getIsbn() + "\n   Description: " +
                        booksStore.get(i).getShortDescription() + "\n   Status: " +
                        booksStore.get(i).getStatus());
                System.out.println();
            }
        }
    }
    public ArrayList<Books> getBooksList() {
        double sum = 0.0;
        if(booksList.size() <= 0 || booksList == null) {
            System.out.println("no books");
            return null;
        }else{
            return booksList;
//            System.out.println("--Order Book--");
//            for(int i = 0; i < booksList.size(); i++) {
//
////                System.out.println(i+1 + ". " + booksList.get(i).getTitle() + " " + booksList.get(i).getPrice());
////                sum = sum + booksList.get(i).getPrice();
////                System.out.println();
//            }
////            System.out.println("sum total books = " + sum);
        }
//        scan.nextLine(); //next
    }
    public void addBooks(int index, String username){
        if (booksStore.size() >= index && index-1 >= 0) {
            booksList.add(booksStore.get(index - 1));
            savebuyerJson(username);
        }
    }
    public void removeBooks(int index, String username){
        if (booksList.size() <= 0){
            System.out.println("no books");
        } else {
            if(booksList.size() > index-1) {
                booksList.remove(index - 1);
                savebuyerJson(username);
            }
        }
    }
    public ArrayList<Books> readBooks(String search){
        ArrayList<Books> temp = new ArrayList<>();
        if (booksStore.size() <= 0){
            System.out.println("no books");
        }else {
            for(int i = 0; i < booksStore.size(); i++) {
                String title = booksStore.get(i).getTitle().toLowerCase();
                List<String> category = booksStore.get(i).getCategories();
                List<String> authors = booksStore.get(i).getAuthors();
                String searching= search.toLowerCase();
                boolean existCategory= false;
                boolean existAuthors= false;

                for(String cate: category){
                    if (cate.toLowerCase().equals(searching.toLowerCase())){
                        existCategory = true;
                    }
                }

                for(String author: authors){
                    if (author.toLowerCase().equals(searching.toLowerCase())){
                        existAuthors = true;
                    }
                }
                if(existCategory || existAuthors) {
                    System.out.println("category " + existCategory);
                    System.out.println("authors " + existAuthors);

                }
                if (searching.equals(title) ||existCategory ||existAuthors){
                    System.out.println("found books:" );

                    System.out.println( );

                    System.out.println( i+1 + ". Title: " +
                            booksStore.get(i).getTitle() + "\n   Price: " +
                            booksStore.get(i).getPrice() + "\n   Categories: " +
                            booksStore.get(i).getCategories()  + "\n   Page Count: " +
                            booksStore.get(i).getPageCount() + "\n   Authors: " +
                            booksStore.get(i).getAuthors() + "\n   ISBN: " +
                            booksStore.get(i).getIsbn() + "\n   Description: " +
                            booksStore.get(i).getShortDescription() + "\n   Status: " +
                            booksStore.get(i).getStatus());
                    System.out.println();
                    temp.add(booksStore.get(i));
                }
            }
        }
        return temp;
    }

    //pagination
    public List<Books> productsPaginated(int start, int pagesize){
        ArrayList<Books> listBook = new ArrayList<Books>(booksStore);

        return listBook.subList(start, start + pagesize);
    }

    //database json
    public ArrayList<Books> reloadstoreJson(){
        try {
            // create Gson instance
            Gson gson = new Gson();

            ArrayList<Books> loadBooks = gson.fromJson(new FileReader(filepath + "Books.json"), new TypeToken<ArrayList<Books>>() {}.getType());
            return loadBooks;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public double finalizeOrder(String name, double payment){
        double sum = 0.0;
        double change = 0.0;
        if(booksList.size() <= 0 ){
            System.out.println("you haven't choose a book");
        }
        else {
            System.out.println("Your Order");
            for (int i = 0; i < booksList.size(); i++) {
                System.out.println(i + 1 + ". " + booksList.get(i).getTitle() + " " + booksList.get(i).getPrice());
                sum = sum + booksList.get(i).getPrice();
                System.out.println();
            }
            System.out.println("total price books: " + sum);
            change = payment - sum;
            if (change < 0) {
                System.out.println("sorry, not enough money");
            } else {
                System.out.println("your change: " + change);
            }
        }
        savebuyerJson(name);
        return sum;

    }

    //database json
    public ArrayList<Books> reloadbuyerJson(String name){
        try {
            Gson gson = new Gson();

            ArrayList<Books> loadBooks = gson.fromJson(new FileReader(filepath + "Buyers\\Buyer_" + name + ".json"), new TypeToken<ArrayList<Books>>() {}.getType());
            return loadBooks;
        } catch (Exception ex) {
            return booksList;
        }
    }
    public void savebuyerJson(String name) {
        if (name != null) {
            try {
                Gson gson = new Gson();
                file = new FileWriter(filepath + "Buyers\\Buyer_" + name + ".json");
                gson.toJson(booksList, file);

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
        } else{
            System.out.println("you have to login first");
//            userAuth authenticate = new userAuth();
//            authenticate.userLogin();
        }
    }

}
