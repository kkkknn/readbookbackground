package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.service.BookService;
import com.example.readbookbackground.util.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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


    @ResponseBody
    @RequestMapping("/AddFavoriteBook")
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
    @RequestMapping("/GetChapter")
    public String getChapter(String chapterUrl,int mode){
        if(chapterUrl==null||chapterUrl.equals("")){
            return null;
        }
        Logger logger=LoggerFactory.getLogger(BookController.class);
        logger.error("测试11111");
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
