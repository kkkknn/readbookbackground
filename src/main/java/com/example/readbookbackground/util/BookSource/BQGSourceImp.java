package com.example.readbookbackground.util.BookSource;


import com.alibaba.fastjson.JSONObject;

public interface BQGSourceImp {
    JSONObject getBookInfo(String url);
    JSONObject searchBooks(String str,int page);
    void getAllChapter(String bookUrl);
    void getChapter(String chapterUrl);
}
