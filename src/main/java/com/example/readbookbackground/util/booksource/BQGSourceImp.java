package com.example.readbookbackground.util.booksource;


import com.alibaba.fastjson.JSONObject;

public interface BQGSourceImp {
    JSONObject getBookInfo(String url);
    JSONObject searchBooks(String str,int page);
    void getAllChapter(String bookUrl);
    void getChapter(String chapterUrl);
    String getBookImg(String imgUrl);
}
