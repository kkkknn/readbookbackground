package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
    public String searchBook(String str,int page){
        if(str==null||str.equals("")){
            return null;
        }
        //根据图书名字查询，根据作者名字查询 每页10条数据
        JSONObject retObject=bookService.searchBook(str,page,10);
        return getResponseJSON(retObject).toString();
    }

    @ResponseBody
    @RequestMapping("/getBookInfo")
    public String getBookInfo(String bookUrl,int mode){
        if(bookUrl==null||bookUrl.equals("")){
            return null;
        }
        JSONObject retObject=bookService.getBookInfo(bookUrl,mode);
        return getResponseJSON(retObject).toString();
    }


    @ResponseBody
    @RequestMapping("/addFavoriteBook")
    public String addFavoriteBook(int accountId,String bookUrl,int mode){
        //添加相关图书到数据库中，并返回是否成功
        boolean flag=bookService.addFavoriteBook(accountId,bookUrl,mode);
        JSONObject retObject=new JSONObject();
        if(flag){
            retObject.put("code","success");
            retObject.put("data",bookUrl+"添加成功!");
            return retObject.toString();

        }else {
            retObject.put("code", "error");
            retObject.put("data", "错误原因");
            return retObject.toString();
        }
    }

    @ResponseBody
    @RequestMapping("/getChapter")
    public String getChapter(String chapterUrl,int mode){
        if(chapterUrl==null||chapterUrl.equals("")){
            return null;
        }
        JSONObject retObject=bookService.getChapter(chapterUrl,mode);
        return getResponseJSON(retObject).toString();
    }



    private JSONObject getResponseJSON(JSONObject object){
        JSONObject retObject=new JSONObject();
        if(object!=null){
            retObject.put("code","success");
            retObject.put("data",object);
            return retObject;
        }else{
            retObject.put("code","error");
            retObject.put("data","错误原因");
            return retObject;
        }
    }

}
