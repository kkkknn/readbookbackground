package com.example.readbookbackground.controller;

import com.example.readbookbackground.service.BookService;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
/*
 * 主要分为以下几个接口
 * 1. 搜索，查询几个平台并返回搜索结果，
 * 2. 收藏，根据搜索结果把相应需要收藏追更的图书进行存储记录
 * 3. 缓存下载，下载收藏的图书，（由于爬取的原因，当前收藏完不能直接进行缓存）
 * 4. 书籍详情（暂时不加）
 * */

@RequestMapping("/Book")
@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseBody
    @RequestMapping("/SearchBook")
    public String searchBook(String str,int mode,int page){
        if(str==null||str.equals("")){
            return null;
        }
        JSONObject retObject=bookService.searchBook(str,mode,page);
        return getResponseJSON(retObject).toString();
    }

    @ResponseBody
    @RequestMapping("/GetBookInfo")
    public String getBookInfo(String bookUrl,int mode){
        if(bookUrl==null||bookUrl.equals("")){
            return null;
        }
        JSONObject retObject=bookService.getBookInfo(bookUrl,mode);
        return getResponseJSON(retObject).toString();
    }


    private JSONObject getResponseJSON(JSONObject object){
        JSONObject retObject=new JSONObject();
        if(object!=null){
            try {
                retObject.put("code","success");
                retObject.put("data",object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retObject;
        }else{
            try {
                retObject.put("code","error");
                retObject.put("data","错误原因");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retObject;
        }
    }

}
