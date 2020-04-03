package com.example.readbookbackground.enums;


import java.sql.Date;

public class BookInfo {
    private int book_id;
    private String book_name;
    private String book_img;
    private Date book_update_time;
    private String book_author_name;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public Date getBook_update_time() {
        return book_update_time;
    }

    public void setBook_update_time(Date book_update_time) {
        this.book_update_time = book_update_time;
    }

    public String getBook_author_name() {
        return book_author_name;
    }

    public void setBook_author_name(String book_author_name) {
        this.book_author_name = book_author_name;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", book_img='" + book_img + '\'' +
                ", book_update_time=" + book_update_time +
                ", book_author_name='" + book_author_name + '\'' +
                '}';
    }
}
