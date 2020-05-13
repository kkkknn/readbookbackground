package com.example.readbookbackground.enums;


import java.sql.Timestamp;

public class BookInfo {
    private int book_id;
    private String book_name;
    private String book_img;
    private Timestamp book_update_time;
    private String book_author_name;
    private String book_url;
    private int book_source_id;
    private int book_chapter_count;

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public int getSource_id() {
        return book_source_id;
    }

    public void setSource_id(int source_id) {
        this.book_source_id = source_id;
    }

    public int getChapter_count() {
        return book_chapter_count;
    }

    public void setChapter_count(int chapter_count) {
        this.book_chapter_count = chapter_count;
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

    public String getBook_img() {
        return book_img;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public Timestamp getBook_update_time() {
        return book_update_time;
    }

    public void setBook_update_time(Timestamp book_update_time) {
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
                ", book_url='" + book_url + '\'' +
                ", source_id=" + book_source_id +
                ", chapter_count=" + book_chapter_count +
                '}';
    }
}
