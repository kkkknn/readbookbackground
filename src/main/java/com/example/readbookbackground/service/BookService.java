package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BookService {
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookMapper bookMapper, AccountMapper accountMapper) {
        this.bookMapper = bookMapper;
    }

    public JSONObject searchBook(String str,int mode,int page){

        return null;
    }

    public JSONObject getBookInfo(String url, int mode){

        return null;
    }

    public JSONObject getChapter(String url, int mode){

        return null;
    }


    public boolean addFavoriteBook(int accountId,String bookUrl,int mode){

        return false;
    }

}
