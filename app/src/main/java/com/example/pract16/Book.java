package com.example.pract16;

public class Book {
    private int ID_Book;
    private String Book_Name;
    private String Book_Author;

    public Book(int ID_Book, String Book_Name, String Book_Author){
        this.ID_Book = ID_Book;
        this.Book_Name = Book_Name;
        this.Book_Author = Book_Author;
    }
    public int getID_Book() {
        return ID_Book;
    }
    public String getBook_Name(){
        return Book_Name;
    }
    public String getBook_Author() {
        return Book_Author;
    }

    public void setID_Book(int ID_Book){
        this.ID_Book = ID_Book;
    }
    public void setBook_Name(String Book_Name){
        this.Book_Name = Book_Name;
    }
    public void setBook_Author(String Book_Author){
        this.Book_Author = Book_Author;
    }
}