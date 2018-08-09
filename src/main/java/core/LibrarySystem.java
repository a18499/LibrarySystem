package core;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.stream.Stream;


public class LibrarySystem {
    private ArrayList<Book> books = new ArrayList<>();
   // private HashMap<String,ArrayList<Book>> borrowList = new HashMap<>();

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

   /* public void setBorrowList(HashMap<String, ArrayList<Book>> borrowList) {
        this.borrowList = borrowList;
    }

    public HashMap<String, ArrayList<Book>> getBorrowList() {
        return borrowList;
    }*/

    public String  addBook(String author, String subject){

            Book newBook = new Book();
            newBook.setAuthor(author);
            newBook.setId(books.size());
            newBook.setSubject(subject);
            books.add(newBook);
        return "success";
    }

    public String removeBook(int book_id){

        Book book_about_to_remove=null;
        for (Book eachbook:books) {
            if(eachbook.getId()==book_id){
                book_about_to_remove = eachbook;
            }
        }
        if(book_about_to_remove!=null){
           // System.out.println("book_about_to_remove: "+book_about_to_remove.getId());
            books.remove(book_about_to_remove);
        }

        return "success";
    }
    public String checkoutBook(int book_id,String userName){

        //for(int each_book_id:book_ids){
            Book readyToCheckOut = books.get(book_id);
            if(readyToCheckOut.getBorrowRecords().size()>0){
                BorrowInfo latestRecord =readyToCheckOut.getBorrowRecords().get(readyToCheckOut.getBorrowRecords().size()-1);
                if(latestRecord.getStatus().equals("CheckOut")){
                    return "Can not checkout since the book is checked out ";
                }

            }
            BorrowInfo newBorrowInfo = new BorrowInfo();
            newBorrowInfo.setBorrowerName(userName);
            newBorrowInfo.setStatus("CheckOut");
           // System.out.println("addBorrowRecord to librarySystem");
            readyToCheckOut.addBorrowRecord(newBorrowInfo);


       // }
        return "Success";
    }
    public String returnBook(int book_id){
        Book book_ready_to_return = books.get(book_id);
        //System.out.println("Book Ready To Return "+book_ready_to_return.getAuthor()+" "+book_ready_to_return.getSubject());
       // String borrowerName = book_ready_to_return.getBorrowRecords().get(book_ready_to_return.getBorrowRecords().size()-1).getBorrowerName();
        //System.out.println("Book Records "+book_ready_to_return.getBorrowRecords().size());
        for(BorrowInfo eachRecord:book_ready_to_return.getBorrowRecords()){
           // System.out.println("Borrower: "+eachRecord.getBorrowerName()+" Status: "+eachRecord.getStatus());
            if (eachRecord.getStatus().equals("CheckOut")){
               // System.out.println("Borrower "+ eachRecord.getBorrowerName());
                book_ready_to_return.updateBoorrowRecord(eachRecord.getBorrowerName(),"Return");
            }
        }

        return "success";
    }

    public void listAuthor(String author){
        ArrayList<Book> bookWithAuthor = new ArrayList<>();
        for(Book eachBook:books){
            if(eachBook.getAuthor().equals(author)){
                bookWithAuthor.add(eachBook);
            }
        }
        for(Book each : bookWithAuthor){
            System.out.println("ID: "+each.getId()+" Author: "+each.getAuthor()+" Subject: "+each.getSubject());

        }

    }
    public void listSubject(String subJect){
        ArrayList<Book> bookWithsubject = new ArrayList<>();

        for (Book eachbook:books){
            if(eachbook.getSubject().equals(subJect)){
                bookWithsubject.add(eachbook);
            }
        }

        for(Book each : bookWithsubject){
            System.out.println("ID: "+each.getId()+" Author: "+each.getAuthor()+" Subject: "+each.getSubject());

        }

    }

    public ArrayList<Book>  findChecked(String userName2){
     // String  = System.out.println("ID: "+each.getId()+" Author: "+each.getAuthor()+" Subject: "+each.getSubject());
        ArrayList<Book> results = new ArrayList<>();

        for (Book eachbook:books) {
            //System.out.println("Book: "+eachbook.getSubject()+" Borrow Record"+eachbook.getBorrowRecords().size());
            if(eachbook.getBorrowRecords().size()>0){
                for (BorrowInfo eachInfo:eachbook.getBorrowRecords()){
                    //System.out.println("Borrower: "+eachInfo.getBorrowerName()+" Status: "+eachInfo.getStatus());
                    if(eachInfo.getBorrowerName().equals(userName2)&&eachInfo.getStatus().equals("CheckOut")){
                        results.add(eachbook);
                        //System.out.println("Fined");
                    }
                }


            }

        }
        return results;
    }



    public String findBorrower(int bookid){

        Book theBook = books.get(bookid);
        BorrowInfo borrowInfo =theBook.getBorrowRecords().get(theBook.getBorrowRecords().size()-1);

        return borrowInfo.getBorrowerName();
    }
}
