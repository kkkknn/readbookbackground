package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class BookService {
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookMapper bookMapper, AccountMapper accountMapper) {
        this.bookMapper = bookMapper;
    }

    public ArrayList<BookInfo> searchBook(String str, int page_index, int count){
        //todo:正则表达式判断 图书名、作者名是否合法，有无sql注入现象出现
        if(page_index<0){
            return null;
        }
        return bookMapper.selectBook(str,page_index*count,count);
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
