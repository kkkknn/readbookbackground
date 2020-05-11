package com.example.readbookbackground.util.booksource.items;

public class SearchBookInfo {
    private String book_name;
    private String author_name;
    private String book_about;
    private String img_url;
    private String book_url;


    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getBook_about() {
        return book_about;
    }

    public void setBook_about(String book_about) {
        this.book_about = book_about;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "SearchBookInfo{" +
                "book_name='" + book_name + '\'' +
                ", author_name='" + author_name + '\'' +
                ", book_about='" + book_about + '\'' +
                ", book_url='" + book_url + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
