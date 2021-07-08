package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.enums.ChapterInfo;
import com.example.readbookbackground.enums.FavoriteInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.mapper.BookMapper;
import com.example.readbookbackground.util.FileUtil;
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

    public ArrayList<BookInfo> searchBook(String str, int pageIndex, int pageSize){
        if(pageIndex<0){
            return null;
        }
        return bookMapper.selectBook("%"+str+"%",(pageIndex-1)*pageSize,pageSize);
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

    public String getChapter(String url){
        return FileUtil.getChapterContent(url);
    }

    public ArrayList<ChapterInfo> getChapterList(int bookId,int pageIndex,int pageSize){
        if(pageIndex<0){
            return null;
        }
        return bookMapper.getChapterList(bookId,(pageIndex-1)*pageSize,pageSize);
    }


    /**
     * 收藏图书
     * @param   favoriteInfo 收藏图书信息
     * @return  boolean类型 是否成功
     */
    public boolean addFavoriteBook(FavoriteInfo favoriteInfo){
        //检查是否添加成功过
        if(favoriteInfo==null||bookMapper.checkFavoriteBook(favoriteInfo)!=null){
            return false;
        }else {
            return bookMapper.insertFavoriteBook(favoriteInfo)==1;
        }
    }

    public boolean removeFavoriteBook(int favoriteId){
        if(favoriteId<0){
            return false;
        }else {
            return bookMapper.deleteFavoriteBook(favoriteId)==1;
        }
    }

    public BookInfo addBookInfo(BookInfo bookInfo){
        //添加图书详情，并写入成功后的bookID
        int id=bookMapper.insertBook(bookInfo);
        System.out.println(bookInfo.getBook_id());
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
        System.out.println("添加结果："+val);
        return val>0;
    }

    public boolean updateChapterSum(int book_id, int sum,String near_chapter_name) {
        return bookMapper.updateChapterSum(book_id,sum,near_chapter_name)==1;
    }
}
