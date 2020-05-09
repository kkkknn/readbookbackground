package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.mapper.BookMapper;
import com.example.readbookbackground.util.BookSource.BQGSourceImp;
import com.example.readbookbackground.util.BookSource.Source1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookService {
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public JSONObject searchBook(String str,int mode,int page){
        BQGSourceImp bqgSourceImp=getBQGSource(mode);
        if(bqgSourceImp==null){
            return null;
        }
        return bqgSourceImp.searchBooks(str,page);
    }

    public JSONObject getBookInfo(String url, int mode){
        BQGSourceImp bqgSourceImp=getBQGSource(mode);
        if(bqgSourceImp==null){
            return null;
        }
        return bqgSourceImp.getBookInfo(url);
    }

    private BQGSourceImp getBQGSource(int mode){
        switch (mode){
            case 1:
                return new Source1();
            default:
                return null;
        }
    }

}
