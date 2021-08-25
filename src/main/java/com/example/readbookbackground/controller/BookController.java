package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.enums.ChapterInfo;
import com.example.readbookbackground.enums.FavoriteInfo;
import com.example.readbookbackground.service.BookService;
import com.example.readbookbackground.util.FileUtil;
import com.example.readbookbackground.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
                JSONArray jsonArray=new JSONArray();
                jsonArray.addAll(arrayList);
                jsonObject.put("data",jsonArray.toJSONString());

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
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(list);
            jsonObject.put("data",jsonArray.toJSONString());
        }else {
            jsonObject.put("code","error");
            jsonObject.put("data","未查询到相关章节");
        }
        return jsonObject.toString();
    }


    @ResponseBody
    @RequestMapping("/addFavoriteBook")
    public String addFavoriteBook(int book_id,int account_id){
        JSONObject retObject=new JSONObject();
        //添加相关图书到数据库中，并返回是否成功
        int flag=bookService.addFavoriteBook(book_id,account_id);
        switch (flag){
            case 0:
                retObject.put("code", "error");
                retObject.put("data", "已收藏，请勿重复收藏");
                break;
            case -1:
                retObject.put("code", "error");
                retObject.put("data", "加入收藏失败");
                break;
            case 1:
                retObject.put("code","success");
                retObject.put("data","加入收藏成功");
                break;

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
        JSONArray jsonArray=bookService.getChapter(chapter_path);
        if(jsonArray==null||jsonArray.size()==0){
            jsonObject.put("code","error");
            jsonObject.put("data","读取章节错误");
        }else {
            jsonObject.put("code","success");
            jsonObject.put("data",jsonArray);
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping("/getFavoriteBook")
    public String getFavoriteBook(int accountId){
        JSONObject retObject=new JSONObject();
        ArrayList<BookInfo> arrayList =bookService.getFavoriteBook(accountId);
        if(!arrayList.isEmpty()){
            retObject.put("code","success");
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(arrayList);
            retObject.put("data",jsonArray.toJSONString());
        }
        return retObject.toString();
    }

    @ResponseBody
    @GetMapping("/downloadBookImage")
    public void downloadAPK(HttpServletResponse response, String urlPath) {
        FileUtil.downloadFile(response, urlPath);
    }

}
