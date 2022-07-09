package com.Obook.Controller;

import com.Obook.Model.Books;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.Obook.BooksAct.*;
import static com.Obook.Controller.AccountController.accountController;
import static com.Obook.userAuth.self;


@Controller
public class BookController {

    @GetMapping("/MainPage")
    String getProducts(Model model) {
//        if (self == null) {
//            System.out.println("you should login first");
//            return "login";
//        }
        ArrayList<Books> passVal = booksStore;
        model.addAttribute("product", passVal);
        return "MainPage";
    }

    @PostMapping("/choose")
    String addBook(@ModelAttribute("choose") String input, Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }else if(input != null) {
            int value = Integer.parseInt(input);
            booksAct.addBooks(value, self.getUsername());
        }
        getProducts(model);
        return "MainPage";
    }

    @PostMapping("/remove")
    String removeBook(@ModelAttribute("remove") String input, Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        booksAct.removeBooks(Integer.parseInt(input), self.getUsername());

//        Books rem = booksAct.removeBooks(Integer.parseInt(input), self.getUsername());
//        if (rem != null) {
//            model.addAttribute("remove", rem.getTitle());
//        } else {
//            model.addAttribute("remove", "you not choose this book");
//        }

        getBooksList(model);
        return "listBooks";
    }

    @RequestMapping("/searchpage")
    String searchpage() {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        return "search";
    }

    @PostMapping("/searchpage/search")
    String searchBook(@ModelAttribute("search") String input, Model model) {
        ArrayList<Books> temp = booksAct.readBooks(input);
        model.addAttribute("searchBook", temp);
        return "search";
    }

    @GetMapping("/getBooksList")
    String getBooksList(Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        ArrayList<Books> list = booksAct.getBooksList();
        model.addAttribute("listBook", list);
        return "listBooks";
    }


    @RequestMapping("/finalizepage")
    String finalizeorder(Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        getBooksList(model);
        model.addAttribute("buyer", "Buyer: " + self.getUsername());

        return "finalize";
    }

    @PostMapping("/finalizepage/finalize")
    String finalizeorder(@RequestParam String pay, Model model) {
        if (self == null) {
            System.out.println("you should login first");
            return "login";
        }
        getBooksList(model);
        model.addAttribute("buyer", "Buyer: " + self.getUsername());

        double payment = Double.parseDouble(pay);
        double total = booksAct.finalizeOrder(self.getUsername(), payment);
        model.addAttribute("totalPrice", "Total price: "+ total);
        double change = payment - total;
        if (change < 0) {
            model.addAttribute("change", "sorry not enough money");
        } else {
            model.addAttribute("change", "your change: " + change);
        }

        return "finalize";
    }

}