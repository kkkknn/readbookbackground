package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.AccountInfo;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.mapper.BookMapper;
import com.example.readbookbackground.util.booksource.BQGSourceImp;
import com.example.readbookbackground.util.booksource.Source1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
public class BookService {
    private final BookMapper bookMapper;
    private final AccountMapper accountMapper;

    @Autowired
    public BookService(BookMapper bookMapper, AccountMapper accountMapper) {
        this.bookMapper = bookMapper;
        this.accountMapper = accountMapper;
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

    public boolean addFavoriteBook(String accountName,String bookUrl,int mode){
        if(accountName==null||bookUrl==null||accountName.equals("")||bookUrl.equals("")){
            return false;
        }
        //确定用户、图书链接是否有效
        AccountInfo accountInfo=accountMapper.selectUser(accountName);
        BQGSourceImp bqgSourceImp=getBQGSource(mode);
        if(accountInfo==null||bqgSourceImp==null){
            return false;
        }
        JSONObject jsonObject=bqgSourceImp.getBookInfo(bookUrl);
        if(jsonObject==null){
            return false;
        }
        String timeStr=jsonObject.getString("updateTime");
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(strDateFormat);
        Date dateS=null;
        try {
            java.util.Date dateU=simpleDateFormat.parse(timeStr);
            dateS=new Date(dateU.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String imgUrl=jsonObject.getString("bookImg");

        BookInfo bookInfo=new BookInfo();
        bookInfo.setBook_author_name(jsonObject.getString("authorName"));
        bookInfo.setBook_img(bqgSourceImp.getBookImg(imgUrl));
        bookInfo.setBook_name(jsonObject.getString("bookName"));
        bookInfo.setBook_update_time(dateS);
        return false;
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
