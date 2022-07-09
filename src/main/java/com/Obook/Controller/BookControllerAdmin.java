package com.Obook.Controller;

import com.Obook.BooksActAdmin;
import com.Obook.Model.Books;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Obook.BooksActAdmin.booksActAdmin;
import static com.Obook.userAuth.self;


@Controller
public class BookControllerAdmin {

    @GetMapping("/adminPage")
    String getProducts(Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        ArrayList<Books> passVal = BooksActAdmin.booksStoreAdmin;
        model.addAttribute("listBooks", passVal);
        return "adminPage";
    }

    @RequestMapping("/add-adminpage")
    String addBook() {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        return "addAdmin";
    }

    @PostMapping("/add-adminpage/add-admin")
    String addBook(@RequestParam  String title,
                   @RequestParam  String isbn,
                   @RequestParam  String pageCount,
                   @RequestParam  String publishedDate,
                   @RequestParam  String shortDesc,
                   @RequestParam  String longDesc,
                   @RequestParam  String status,
                   @RequestParam  String authors,
                   @RequestParam  String categories,
                   @RequestParam  String price,
                   Model model) {
        if (self == null ) {
            System.out.println("you should login first");
            return "login";
        }
        List<String> ListAuthors = Arrays.asList(authors.split(","));
        List<String> ListCategories = Arrays.asList(categories.split(","));
        booksActAdmin.addBooksAdmin(title, isbn, Integer.parseInt(pageCount) ,publishedDate, shortDesc, longDesc,status, ListAuthors, ListCategories, Double.parseDouble(price));
        getProducts(model);
        return "addAdmin";
    }

    @PostMapping("/remove-admin")
    String removeBook(@ModelAttribute("remove-admin") String input, Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        System.out.println("remove books");
        booksActAdmin.removeBooksAdmin(Integer.parseInt(input));
        return "searchAdmin";
    }

    @RequestMapping("/search-adminpage")
    String searchpage() {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        return "searchAdmin";
    }

    @PostMapping("/search-adminpage/search-admin")
    String searchBook(@ModelAttribute("search-admin") String input, Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        ArrayList<Books> temp = booksActAdmin.readBooksAdmin(input);
        model.addAttribute("searchBookAdmin", temp);
        return "searchAdmin";
    }
}