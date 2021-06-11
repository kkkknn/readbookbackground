package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.service.BookService;
import com.example.readbookbackground.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            if(arrayList.size()>0){
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

    @ResponseBody
    @RequestMapping("/getChapterList")
    public String getChapterList(int bookId,int pageCount,int pageSize){
        //只返回当前章节数量，最新章节名称及更新日期 以及其他图书信息
        if(bookId<=0){
            return null;
        }
        JSONObject retObject=bookService.getChapterList(bookId,pageCount,pageSize);
        return getResponseJSON(retObject).toString();
    }


    @ResponseBody
    @RequestMapping("/addFavoriteBook")
    public String addFavoriteBook(int accountId,int bookId){
        //添加相关图书到数据库中，并返回是否成功
        boolean flag=bookService.addFavoriteBook(accountId,"bookUrl",2);
        JSONObject retObject=new JSONObject();
        if(flag){
            retObject.put("code","success");
            retObject.put("data","添加成功!");
            return retObject.toString();

        }else {
            retObject.put("code", "error");
            retObject.put("data", "错误原因");
            return retObject.toString();
        }
    }

    @ResponseBody
    @RequestMapping("/removeFavoriteBook")
    public String removeFavoriteBook(int accountId,int bookId){
        //添加相关图书到数据库中，并返回是否成功
        boolean flag=bookService.removeFavoriteBook(accountId,"bookUrl",2);
        JSONObject retObject=new JSONObject();
        if(flag){
            retObject.put("code","success");
            retObject.put("data","添加成功!");
            return retObject.toString();

        }else {
            retObject.put("code", "error");
            retObject.put("data", "错误原因");
            return retObject.toString();
        }
    }


    @ResponseBody
    @RequestMapping("/getChapterContent")
    public String getChapterContent(String chapterPath){
        if(chapterPath.isEmpty()){
            return null;
        }
        //读取本地文件然后将内容放置接送字符串中
        JSONObject retObject=bookService.getChapter("chapterUrl",2);

        return getResponseJSON(retObject).toString();
    }



    private JSONObject getResponseJSON(JSONObject object){
        JSONObject retObject=new JSONObject();
        if(object!=null){
            retObject.put("code","success");
            retObject.put("data",object);
        }else{
            retObject.put("code","error");
            retObject.put("data","错误原因");
        }

        return retObject;
    }

}
