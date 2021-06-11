package com.example.readbookbackground.enums;


import java.sql.Timestamp;

public class BookInfo {
    private int book_id;
    private String book_name;
    private String book_img_url;
    private String author_name;
    private String book_url;
    private int book_chapter_sum;
    private String source_name;
    private String book_near_chapter_name;

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }


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

    public String getBook_img_url() {
        return book_img_url;
    }

    public void setBook_img_url(String book_img_url) {
        this.book_img_url = book_img_url;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public int getBook_chapter_sum() {
        return book_chapter_sum;
    }

    public void setBook_chapter_sum(int book_chapter_sum) {
        this.book_chapter_sum = book_chapter_sum;
    }

    public String getBook_near_chapter_name() {
        return book_near_chapter_name;
    }

    public void setBook_near_chapter_name(String book_near_chapter_name) {
        this.book_near_chapter_name = book_near_chapter_name;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", book_img_url='" + book_img_url + '\'' +
                ", author_name='" + author_name + '\'' +
                ", book_url='" + book_url + '\'' +
                ", book_chapter_sum=" + book_chapter_sum +
                ", source_name='" + source_name + '\'' +
                ", book_near_chapter_name='" + book_near_chapter_name + '\'' +
                '}';
    }
}
