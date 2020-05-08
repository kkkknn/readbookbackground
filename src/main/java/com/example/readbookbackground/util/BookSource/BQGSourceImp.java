package com.example.readbookbackground.util.BookSource;


import net.sf.json.JSONObject;

public interface BQGSourceImp {
    JSONObject getBookInfo(String url);
    JSONObject searchBooks(String str,int page);
    void getAllChapter(String bookUrl);
    void getChapter(String chapterUrl);
}
