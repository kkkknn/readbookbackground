package com.example.readbookbackground.enums;

public class ChapterInfo {
    private int chapter_id;
    private String chapter_name;
    private String chapter_content;
    private int book_id;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_content() {
        return chapter_content;
    }

    public void setChapter_content(String chapter_content) {
        this.chapter_content = chapter_content;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    @Override
    public String toString() {
        return "ChapterInfo{" +
                "chapter_id=" + chapter_id +
                ", chapter_name='" + chapter_name + '\'' +
                ", chapter_content='" + chapter_content + '\'' +
                ", book_id=" + book_id +
                '}';
    }
}
