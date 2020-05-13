package com.example.readbookbackground.util.booksource;


import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface BQGSourceImp {
    JSONObject getBookInfo(String url);
    JSONObject searchBooks(String str,int page);
    JSONObject getChapter(String chapterUrl);
    String getBookImg(String imgUrl);
}
