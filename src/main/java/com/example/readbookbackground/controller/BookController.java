package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.enums.ChapterInfo;
import com.example.readbookbackground.enums.FavoriteInfo;
import com.example.readbookbackground.service.BookService;
import com.example.readbookbackground.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;


@RequestMapping("/book")
@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseBody
    @RequestMapping("/searchBook")
    public String searchBook(String str,int pageCount,int pageSize){
        JSONObject jsonObject=new JSONObject();
        if(!StringUtil.containsSqlInjection(str)){
            jsonObject.put("code","error");
            jsonObject.put("data","搜索关键字不符合规范");
        }else {
            //根据图书名字查询，根据作者名字查询 每页10条数据 返回
            ArrayList<BookInfo> arrayList =bookService.searchBook(str,pageCount,pageSize);
            if(!arrayList.isEmpty()){
                jsonObject.put("code","success");
                jsonObject.put("data",arrayList.toString());
                System.out.println("测试输出:"+arrayList.toString());
            }
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping("/getBookInfo")
    public String getBookInfo(int bookId){
        //只返回当前章节数量，最新章节名称及更新日期 以及其他图书信息
        JSONObject jsonObject=new JSONObject();
        if(bookId==0){
            jsonObject.put("code","error");
            jsonObject.put("data","搜索关键字不符合规范");
        }else {
            BookInfo bookInfo=bookService.getBookInfo(bookId);
            if(bookInfo!=null){
                jsonObject.put("code","success");
                jsonObject.put("data",bookInfo);
            }
        }
        return jsonObject.toString();
    }

    /**
     * 只返回当前章节数量，最新章节名称及更新日期 以及其他图书信息
     * @param bookId        图书id
     * @param pageCount     页码
     * @param pageSize      每页数量
     * @return              JSON字符串
     */
    @ResponseBody
    @RequestMapping("/getChapterList")
    public String getChapterList(int bookId,int pageCount,int pageSize){
        JSONObject jsonObject=new JSONObject();
        if(bookId<=0||pageCount<0||pageSize<=0){
            jsonObject.put("code","error");
            jsonObject.put("data","参数错误");
        }
        ArrayList<ChapterInfo> list=bookService.getChapterList(bookId,pageCount,pageSize);
        if(!list.isEmpty()){
            jsonObject.put("code","success");
            jsonObject.put("data",list.toString());
            System.out.println("测试输出:"+list.toString());
        }else {
            jsonObject.put("code","error");
            jsonObject.put("data","未查询到相关章节");
        }
        return jsonObject.toString();
    }


    @ResponseBody
    @RequestMapping("/addFavoriteBook")
    public String addFavoriteBook(FavoriteInfo favoriteInfo){
        JSONObject retObject=new JSONObject();
        //添加相关图书到数据库中，并返回是否成功
        boolean flag=bookService.addFavoriteBook(favoriteInfo);
        if(flag){
            retObject.put("code","success");
            retObject.put("data","加入收藏成功");

        }else {
            retObject.put("code", "error");
            retObject.put("data", "加入收藏失败");
        }

        return retObject.toString();
    }

    @ResponseBody
    @RequestMapping("/removeFavoriteBook")
    public String removeFavoriteBook(int favorite_id){
        JSONObject retObject=new JSONObject();
        //添加相关图书到数据库中，并返回是否成功
        boolean flag=bookService.removeFavoriteBook(favorite_id);
        if(flag){
            retObject.put("code","success");
            retObject.put("data","取消收藏成功");

        }else {
            retObject.put("code", "error");
            retObject.put("data", "取消收藏失败");
        }
        return retObject.toString();
    }


    @ResponseBody
    @RequestMapping("/getChapterContent")
    public String getChapterContent(String chapter_path){
        JSONObject jsonObject=new JSONObject();
        String content=bookService.getChapter(chapter_path);
        if(content==null){
            jsonObject.put("code","error");
            jsonObject.put("data","读取章节错误");
        }else {
            jsonObject.put("code","success");
            jsonObject.put("data",content);
        }
        return jsonObject.toString();
    }


}
