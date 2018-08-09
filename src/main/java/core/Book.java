package core;

import java.util.ArrayList;

public class Book {
    private int id;
    private String author;
    private String subject;
    private ArrayList<BorrowInfo> borrowRecords = new ArrayList<>();

    public void addBorrowRecord(BorrowInfo borrowInfo){
        try{

        }catch (Exception e){
            System.err.println(e.toString());
        }
        //System.out.println("addBorrowRecord -> "+borrowInfo.getBorrowerName()+" book_Status: "+borrowInfo.getStatus());
        borrowRecords.add(borrowInfo);
    }

    public ArrayList<BorrowInfo> getBorrowRecords() {
        return borrowRecords;
    }

    public void updateBoorrowRecord(String username,String updateValue){
        for(BorrowInfo borrowInfo:borrowRecords){
            if (borrowInfo.getBorrowerName().equals(username)){
                borrowInfo.setStatus(updateValue);
            }
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
