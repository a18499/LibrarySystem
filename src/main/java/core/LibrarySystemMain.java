package core;

import abstracts.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static sun.misc.Version.println;

public class LibrarySystemMain {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private LibrarySystem librarySystem = new LibrarySystem();
    private UserDataBase userDB = new UserDataBase();
    public void init(String path){
        if(Files.exists(Paths.get(path))){
            File input = new File(path);

            try {
                FileReader reader = new FileReader(input);
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                int count=0;
                int bookCount = 0;
                int userCount = 0;
                while (line!=null){
                   // System.out.println(line);
                    if(count == 0){
                        bookCount = Integer.parseInt(line.trim());
                        //System.out.println("BookCount: "+bookCount);
                        count++;
                        line=br.readLine();
                        continue;
                    }else if(count <= bookCount){
                        String[] book = line.split(" ");
                        Book newBook = new Book();
                        newBook.setAuthor(book[0]);
                        newBook.setSubject(book[1]);
                        newBook.setId(books.size()+1);
                        librarySystem.addBook(book[0],book[1]);

                        count++;
                        line=br.readLine();
                        continue;
                    }
                    if(this.librarySystem.getBooks().size()==bookCount&&userCount==0){
                        userCount = Integer.parseInt(line.trim());
                        //System.out.println("UserCount: "+userCount);
                        count++;
                        line=br.readLine();
                        continue;
                    }
                   // System.out.println("Count: "+count+" bookCount+userCount: "+bookCount+userCount);
                    if(count <= bookCount+userCount+1){
                        String[] userInfo = line.split(" ");
                        //System.out.println("userInfo 0: "+userInfo[0]);
                        if(userInfo[0].trim().equals("Staff")){
                            //System.out.println("New staff");
                            Staff staffUser = new Staff();
                            staffUser.setType(userInfo[0]);
                            staffUser.setName(userInfo[1]);
                            this.userDB.addUser(staffUser);
                            users.add(staffUser);
                            count++;
                            line=br.readLine();
                            continue;
                        }else{
                            //System.out.println("New Borrower ");
                            Borrower borrowerUser = new Borrower();
                            borrowerUser.setType(userInfo[0]);
                            borrowerUser.setName(userInfo[1]);
                            borrowerUser.setBorrow_book_number(Integer.parseInt(userInfo[2]));
                            userDB.addUser(borrowerUser);
                            users.add(borrowerUser);
                            count++;
                            line=br.readLine();
                            continue;
                        }

                    }else {
                        //Command parse
                        String[ ] parse_commend = line.split(" ");
                        //System.out.println("parse_commend: "+parse_commend[1]);

                        switch(parse_commend[1]){
                            case "addBook":
                               // System.out.println("User Name: "+parse_commend[0]);
                               // System.out.println("addBook");
                               // System.out.println("Check User Type");

                                //Read next command
                                line=br.readLine();
                               // System.out.println("line: "+line);
                                String[] bookInfo = line.split(" ");
                                if(this.userDB.isUserStaff(parse_commend[0])){
                                    librarySystem.addBook(bookInfo[0],bookInfo[1]);
                                    //System.out.println("add book result: "+);
                                }else{
                                    System.out.println("Borrower can not add book");
                                }

                                break;
                            case "removeBook":
                                //System.out.println("User Name: "+parse_commend[0]);
                                //System.out.println("removeBook");
                                //System.out.println("book_id "+parse_commend[2]);
                                if(this.userDB.isUserStaff(parse_commend[0])){
                                    librarySystem.removeBook(Integer.parseInt(parse_commend[2]));
                                }else{
                                    System.out.println("Borrower can not remove book");
                                }
                                break;
                            case "checkout":
                                //System.out.println("checkout");
                                String[] checkoutCommand = line.split(" ");
                                //System.out.println("checkout User "+checkoutCommand[2]);

                                //Read next command
                                line=br.readLine();
                                //System.out.println("line: "+line);
                                String[] book_ids = line.split(" ");
                                ArrayList<Integer> int_book_ids = new ArrayList<>();
                                for(String eachbookid : book_ids){
                                    int_book_ids.add(Integer.parseInt(eachbookid));
                                }

                                //Check User Type
                                if(this.userDB.isUserStaff(checkoutCommand[0])){
                                    for(User user:this.userDB.getUsers()){
                                        if(user.getType().equals("Borrower")){
                                            Borrower borrower = (Borrower) user;
                                            //System.out.println("int_book_ids "+int_book_ids.size());
                                            for(int bookId:int_book_ids){
                                                int checkedBook = this.librarySystem.findChecked(checkoutCommand[2]).size();
                                                int borrowBookNumber= borrower.getBorrow_book_number();
                                                //System.out.println("borrowBookNumber: "+borrowBookNumber);
                                                //System.out.println("checkedBook: "+checkedBook);
                                                this.librarySystem.checkoutBook(bookId,checkoutCommand[2]);
                                                if(borrowBookNumber==checkedBook){
                                                    System.out.println("Can not check out since the number of books exceed the limitation of user can check-out");
                                                }
                                            }

                                        }
                                    }

                                }else{
                                    System.out.println("User Name: "+checkoutCommand[0]+" is not staff");
                                }


                                break;
                            case "return":
                                //System.out.println("return command");

                                //System.out.println("User Name: "+parse_commend[0]);
                                if(this.userDB.isUserStaff(parse_commend[0])){
                                    //System.out.println("Return book_id: "+parse_commend[2]);
                                    librarySystem.returnBook(Integer.parseInt(parse_commend[2]));
                                    //System.out.println("Result: "+librarySystem.returnBook(Integer.parseInt(parse_commend[2]),parse_commend[0]));
                                }else{
                                    System.out.println("User Name: "+parse_commend[0]+" is not staff");
                                }

                                break;
                            case "listAuthor":
                                //System.out.println("listAuthor command");
                                String[] listAuthorCoomand = line.split(" ");
                                //System.out.println("Author: "+listAuthorCoomand[2]);
                                librarySystem.listAuthor(listAuthorCoomand[2]);
                                break;
                            case "listSubject":
                                //System.out.println("listSubject");
                                String[] listSubjectCoomand = line.split(" ");
                                this.librarySystem.listSubject(listSubjectCoomand[2]);
                                break;
                            case "findChecked":
                                //System.out.println("findChecked");

                                if(this.userDB.isUserStaff(parse_commend[0])&&parse_commend[0]!=parse_commend[2]){
                                    ArrayList<Book> books = this.librarySystem.findChecked(parse_commend[2]);
                                    for(Book each_book:books){
                                        System.out.println("ID: "+each_book.getId()+" Author: "+each_book.getAuthor()+" Subject: "+each_book.getSubject());
                                    }
                                }else{
                                    System.out.println("Borrower can not find books checked out by other users");
                                }
                                break;
                            case "findBorrower":
                               // System.out.println("findBorrower");
                                //System.out.println("Borrower ID: "+parse_commend[2]);
                                if(this.userDB.isUserStaff(parse_commend[0])){
                                    String result = this.librarySystem.findBorrower(Integer.parseInt(parse_commend[2]));
                                    System.out.println("User: "+result);
                                }else{
                                    System.out.println("Borrower can not find borrower");
                                }
                                break;
                        }
                    }

                    count++;
                    line=br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            return;
        }

    }

    public void showContent(){
        for(Book book:librarySystem.getBooks()){
            System.out.println("Author: "+book.getAuthor());
            System.out.println("Subject: "+book.getSubject());
            System.out.println("Book_id: "+book.getId());
            System.out.println("Borrow History");
            for(BorrowInfo borrowInfo:book.getBorrowRecords()){
                System.out.println("Borrower Name: "+borrowInfo.getBorrowerName());
                System.out.println("Status: "+borrowInfo.getStatus());
            }

        }
        System.out.println("User Size "+this.userDB.getUsers().size());
        for(User user:this.userDB.getUsers()){
            System.out.println("Type: "+user.getType());
            System.out.println("Name: "+user.getName());
            if(user.getType().equals("Borrower")){
                Borrower br = (Borrower) user;
                System.out.println("BorrowBook_number:　"+br.getBorrow_book_number());
            }
        }
    }
    public static void main(String[] args){
        //System.out.println("Test");
        if(args.length!=1){
            System.err.println("Argument wrong");
            System.out.println("EX: XX.jar [inputFilePath]");
            return;
        }

        String inputFilePath = args[0];
        LibrarySystemMain test = new LibrarySystemMain();
        test.init(inputFilePath);
        //test.showContent();
    }
}





