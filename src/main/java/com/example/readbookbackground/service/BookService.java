package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.mapper.BookMapper;
import com.example.readbookbackground.util.StringUtil;
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
        if(page_index<0){
            return null;
        }
        return bookMapper.selectBook(str,page_index*count,count);
    }

    public BookInfo checkBook(BookInfo bookInfo){
        if(bookInfo==null
                || StringUtil.containsSqlInjection(bookInfo.getAuthor_name())
                || StringUtil.containsSqlInjection(bookInfo.getSource_name())
                || StringUtil.containsSqlInjection(bookInfo.getBook_name())){
            return null;
        }
        return bookMapper.checkBookInfo(bookInfo.getBook_name(),bookInfo.getAuthor_name(),bookInfo.getSource_name());
    }

    public BookInfo getBookInfo(int bookId){

        return bookMapper.getBookInfo(bookId);
    }

    public JSONObject getChapter(String url, int mode){

        return null;
    }

    public JSONObject getChapterList(int bookId,int pageCount,int pageSize){

        return null;
    }


    public boolean addFavoriteBook(int accountId,String bookUrl,int mode){

        return false;
    }

    public boolean removeFavoriteBook(int accountId,String bookUrl,int mode){

        return false;
    }

    public BookInfo addBookInfo(BookInfo bookInfo){
        //添加图书详情，并写入成功后的bookID
        int id=bookMapper.insertBook(bookInfo);
        System.out.println(id);
        bookInfo.setBook_id(id);
        return bookInfo;
    }

    /**
     * 章节表添加图书章节 添加之后动态增加图书表的总章节数量
     * @param bookId
     * @param chapterName
     * @param chapterPath
     * @return
     */
    public boolean addChapter(int bookId,String chapterName,String chapterPath){

        int val=bookMapper.insertChapter(bookId,chapterName,chapterPath);
        System.out.println("添加详情："+val);


        return val>0;
    }


    public boolean updateChapterSum(int book_id, int sum,String near_chapter_name) {
        return bookMapper.updateChapterSum(book_id,sum,near_chapter_name)==1;
    }
}
