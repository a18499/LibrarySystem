package core;

import abstracts.User;

public class Borrower extends User {
    private int borrow_book_number;

    public void setBorrow_book_number(int borrow_book_number) {
        this.borrow_book_number = borrow_book_number;
    }

    public int getBorrow_book_number() {
        return borrow_book_number;
    }
}
