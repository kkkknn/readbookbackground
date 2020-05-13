package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.AccountInfo;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.mapper.BookMapper;
import com.example.readbookbackground.util.booksource.BQGSourceImp;
import com.example.readbookbackground.util.booksource.Source1;
import com.example.readbookbackground.util.booksource.Source2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookMapper bookMapper, AccountMapper accountMapper) {
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

    public JSONObject getChapter(String url, int mode){
        BQGSourceImp bqgSourceImp=getBQGSource(mode);
        if(bqgSourceImp==null){
            return null;
        }
        return bqgSourceImp.getChapter(url);
    }


    public boolean addFavoriteBook(int accountId,String bookUrl,int mode){
        if(accountId==0||bookUrl==null||bookUrl.equals("")){
            return false;
        }
        BQGSourceImp bqgSourceImp=getBQGSource(mode);
        if(bqgSourceImp==null){
            return false;
        }
        //确定图书链接是否有效
        JSONObject jsonObject=bqgSourceImp.getBookInfo(bookUrl);
        if(jsonObject==null){
            return false;
        }
        //获取图书封面图片并转换成base64字符串
        BookInfo bookInfo=jsonObject2Object(jsonObject);
        String urlStr=jsonObject.getString("bookImg");
        if(urlStr!=null){
            String base64Str=bqgSourceImp.getBookImg(urlStr);
            if(base64Str!=null){
                bookInfo.setBook_img(base64Str);
            }
        }

        //首先进行查重，是否添加过图书
        BookInfo bookInfoR=bookMapper.checkBookInfo(bookInfo.getBook_name(),bookInfo.getBook_author_name(),bookInfo.getSource_id());
        if(bookInfoR==null){
            //从没添加过的进行添加
            if(bookMapper.insertBook(bookInfo)==1){
                //添加图书信息成功后开始记录收藏图书信息
                return bookMapper.insertFavoriteBook(accountId,bookInfo.getBook_id())==1;
            }
        }else{
            System.out.println("添加过重复图书，直接开始添加收藏");
            return bookMapper.insertFavoriteBook(accountId,bookInfoR.getBook_id())==1;
        }

        return false;
    }

    //获取图书来源
    private BQGSourceImp getBQGSource(int mode){
        switch (mode){
            case 1:
                return new Source1();
            case 2:
                return new Source2();
            default:
                return null;
        }
    }

    //json对象转换为实体类
    private BookInfo jsonObject2Object(JSONObject jsonObject){
        if(jsonObject==null){
            return null;
        }
        BookInfo info=new BookInfo();
        String timeStr=jsonObject.getString("updateTime");
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(strDateFormat);
        Timestamp timestamp=null;
        try {
            java.util.Date dateU=simpleDateFormat.parse(timeStr);
            timestamp=new Timestamp(dateU.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        info.setBook_author_name(jsonObject.getString("authorName"));
        info.setBook_name(jsonObject.getString("bookName"));
        info.setBook_update_time(timestamp);
        info.setBook_url(jsonObject.getString("bookUrl"));
        info.setSource_id(jsonObject.getIntValue("bookSourceId"));
        //开始存储图书信息
        Object obj =jsonObject.get("chapterInfo");
        List<String[]> result = new ArrayList<>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                result.add((String[]) o);
            }
        }
        if(result.size()>0){
            info.setChapter_count(result.size());
        }
        return info;
    }

}
