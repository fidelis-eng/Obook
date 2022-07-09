package com.Obook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Scanner;

import static com.Obook.BooksAct.booksAct;
import static com.Obook.userAuth.self;
import static java.lang.System.exit;

@SpringBootApplication
public class Obook {


    public static void main(String[] args) {
        SpringApplication.run(Obook.class, args);
//        userAuth authenticate = new userAuth();
//        authenticate.userLogin();
//        Scanner scan = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n");
//            booksAct.showBooks();
//            System.out.println("\n");
//
//            System.out.println("----Obook----");
//            System.out.println("1. choose book");
//            System.out.println("2. Remove book");
//            System.out.println("3. Search books");
//            System.out.println("4. Show order");
//            System.out.println("5. Finalize order");
//            System.out.println("6. Logout");
//            System.out.println("7. Exit");
//            System.out.println("Menu: ");
//            int input = scan.nextInt();
//
//            switch (input) {
//                case 1:
//                    System.out.println("choose your book: ");
//                    int addTitle = scan.nextInt();
//
//                    booksAct.addBooks(addTitle, self.getUsername());
//                    break;
//                case 2:
//                    booksAct.getBooksList();
//                    System.out.println("Book index: ");
//                    int index = scan.nextInt();
//
//                    booksAct.removeBooks(index, self.getUsername());
//                    break;
//
//                case 3:
//                    String searchBooks = scan.nextLine();
//                    System.out.println("Book title or Book category: ");
//                    searchBooks = scan.nextLine();
//
//                    boolean readList = booksAct.readBooks(searchBooks);
//                    System.out.println(readList);
//                    if (readList) {
//                        System.out.println("choose your book: ");
//                        int title = scan.nextInt();
//
//                        booksAct.addBooks(title, self.getUsername());
//                    }
//                    else{
//                        System.out.println("sorry, can't find your book");
//                        scan.nextLine();
//                    }
//                    break;
//                case 4:
//                    booksAct.getBooksList();
//                    break;
//                case 5:
//                    System.out.println("Payment: ");
//                    double payment = scan.nextDouble();
//
//                    booksAct.finalizeOrder(self.getUsername(), payment);
//                    break;
//                case 6:
//                    authenticate.logout(self.getUsername());
//                    authenticate.userLogin();
//                    break;
//
//                case 7:
//                    //keluar
//                    //exit();
//                    scan.close();
////                    conndb.close();
//                    exit(0);
//                    break;
//
//            }
//
//
//        }
    }


}