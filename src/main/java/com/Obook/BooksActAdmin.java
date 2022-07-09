package com.Obook;

import com.Obook.Model.Books;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Obook.Internal.filepath;

public class BooksActAdmin {
    private static FileWriter file;
    public static ArrayList<Books> booksStoreAdmin = new ArrayList<>();
    public static BooksActAdmin booksActAdmin = new BooksActAdmin();

    public BooksActAdmin(){
        booksStoreAdmin = reloadstoreJson();
    }

    //Books CRUD
    public void showBooksAdmin(){
        if(booksStoreAdmin.size() <= 0) {
            System.out.println("no books");
        }else{
            System.out.println("--List Book--");
            for(int i = 0; i < booksStoreAdmin.size(); i++) {
                System.out.println( i+1 + ". Title: " +
                        booksStoreAdmin.get(i).getTitle() + "\n   Price: " +
                        booksStoreAdmin.get(i).getPrice() + "\n   Categories: " +
                        booksStoreAdmin.get(i).getCategories()  + "\n   Page Count: " +
                        booksStoreAdmin.get(i).getPageCount() + "\n   Authors: " +
                        booksStoreAdmin.get(i).getAuthors() + "\n   ISBN: " +
                        booksStoreAdmin.get(i).getIsbn() + "\n   Description: " +
                        booksStoreAdmin.get(i).getShortDescription() + "\n   Status: " +
                        booksStoreAdmin.get(i).getStatus());
                System.out.println();
            }
        }
    }

    public void addBooksAdmin(String title, String isbn, int pageCount, String publishedDate, String shortDesc, String longDesc, String status, List<String> authors, List<String> categories, double price){
        booksStoreAdmin.add(new Books(booksStoreAdmin.size()+1, title, isbn, pageCount, publishedDate, shortDesc, longDesc, status, authors, categories, price));
        savebookJson();
    }
    public void removeBooksAdmin(int index){
        if (booksStoreAdmin.size() <= 0){
            System.out.println("no books");
        }
        else {
            if(booksStoreAdmin.size() > index-1) {
                booksStoreAdmin.remove(index - 1);
                savebookJson();
            }
        }
    }
    public ArrayList<Books> readBooksAdmin(String search){
        ArrayList<Books> temp = new ArrayList<>();
        if (booksStoreAdmin.size() <= 0){
            System.out.println("no books");
        }else {
            for(int i = 0; i < booksStoreAdmin.size(); i++) {
                String title = booksStoreAdmin.get(i).getTitle().toLowerCase();
                List<String> category = booksStoreAdmin.get(i).getCategories();
                List<String> authors = booksStoreAdmin.get(i).getAuthors();
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
                    System.out.println();
                    System.out.println( i+1 + ". Title: " +
                            booksStoreAdmin.get(i).getTitle() + "\n   Price: " +
                            booksStoreAdmin.get(i).getPrice() + "\n   Categories: " +
                            booksStoreAdmin.get(i).getCategories()  + "\n   Page Count: " +
                            booksStoreAdmin.get(i).getPageCount() + "\n   Authors: " +
                            booksStoreAdmin.get(i).getAuthors() + "\n   ISBN: " +
                            booksStoreAdmin.get(i).getIsbn() + "\n   Description: " +
                            booksStoreAdmin.get(i).getShortDescription() + "\n   Status: " +
                            booksStoreAdmin.get(i).getStatus());
                    System.out.println();
                    temp.add(booksStoreAdmin.get(i));
                }
            }
        }
        return temp;
    }
    public void updateBooksAdmin(int id, String title, String isbn, int pageCount, String publishedDate, String shortDesc, String longDesc, String status, List<String> authors, List<String> categories, double price){
        if (booksStoreAdmin.size() <= 0){
            System.out.println("no books");
        }
        else {
            if(booksStoreAdmin.size() > id-1) {
                Books updateBook = new Books(id-1, title, isbn, pageCount, publishedDate, shortDesc, longDesc, status, authors, categories, price);
                Books oldbook = booksStoreAdmin.get(id-1);

                if (updateBook.get_id() == oldbook.get_id()){
                    if(updateBook.getTitle().equals("")){
                        updateBook.setTitle(oldbook.getTitle());
                    }
                    if(updateBook.getIsbn().equals("")){
                        updateBook.setIsbn(oldbook.getIsbn());
                    }
                    if(updateBook.getPageCount() == 0){
                        updateBook.setPageCount(oldbook.getPageCount());
                    }
                    if(updateBook.getPublishedDate().equals("")){
                        updateBook.setPublishedDate(oldbook.getPublishedDate());
                    }
                    if(updateBook.getShortDescription().equals("")){
                        updateBook.setShortDescription(oldbook.getShortDescription());
                    }
                    if(updateBook.getLongDescription().equals("")){
                        updateBook.setLongDescription(oldbook.getLongDescription());
                    }
                    if(updateBook.getStatus().equals("")){
                        updateBook.setStatus(oldbook.getStatus());
                    }
                    if(updateBook.getPrice() == 0){
                        updateBook.setPrice(oldbook.getPrice());
                    }
                    if(updateBook.getAuthors() == null){
                        updateBook.setAuthors(oldbook.getAuthors());
                    }
                    if(updateBook.getCategories() == null){
                        updateBook.setCategories(oldbook.getCategories());
                    }
                }
                booksStoreAdmin.set(id-1, updateBook);
                savebookJson();
            }
        }
    }
    //pagination
    public List<Books> productsPaginated(int start, int pagesize){
        ArrayList<Books> listBook = new ArrayList<Books>(booksStoreAdmin);

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

    public void savebookJson() {
            try {
                Gson gson = new Gson();
                file = new FileWriter(filepath + "Books.json");
                gson.toJson(booksStoreAdmin, file);

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
