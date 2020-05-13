package com.example.readbookbackground.util.booksource;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.util.booksource.items.SearchBookInfo;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Encoder;
import org.apache.logging.log4j.util.Base64Util;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Source1 implements BQGSourceImp {
    private final String URL="https://www.biqudu.net";
    @Override
    public JSONObject getBookInfo(String url) {
        if(url==null||url.equals("")){
            return null;
        }
        //进行爬取查找
        try {
             Document document=Jsoup.connect(URL+url)
                     .headers(getHeaders())
                     .timeout(8000)
                     .ignoreContentType(true)
                     .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                     .get();
            if(document==null){
                return null;
            }
            //开始解析分析
            JSONObject returnObject=new JSONObject();
            String book_name=document.body().select("#info h1").text();
            if(book_name==null||book_name.equals("")){
                System.out.println("查无此书");
                return null;
            }
            returnObject.put("bookName",book_name);
            returnObject.put("bookUrl",url);
            returnObject.put("bookSourceId",1);
            Elements elements=document.body().select("#info p");
            String author_name=elements.get(0).text().split("：")[1];
            String update_time=elements.get(2).text().split("：")[1];
            String latest_chapter=elements.get(3).select("a").text();
            String img_url=document.body().select("#fmimg img").attr("src");
            returnObject.put("bookImg",img_url);
            returnObject.put("authorName",author_name);
            returnObject.put("updateTime",update_time);
            returnObject.put("latestChapter",latest_chapter);
            //循环添加所有章节
            Elements elementsChapters=document.body().select("#list dl>*");
            boolean isStart=false;
            int count=0;
            String tagName="dt";
            ArrayList<String[]> arrayList=new ArrayList<>();
            for (Element ele:elementsChapters) {
                if(isStart){
                    String chapterUrl=ele.getElementsByTag("a").attr("href");
                    String chapterName=ele.getElementsByTag("a").text();
                    String[] chapterInfo=new String[2];
                    chapterInfo[0]=chapterName;
                    chapterInfo[1]=chapterUrl;
                    arrayList.add(chapterInfo);
                }else {
                    if(tagName.equals(ele.tagName())){
                        if(count==2){
                            isStart=true;
                        }else {
                            count++;
                        }
                    }
                }

            }
            returnObject.put("chapterInfo",arrayList);

            return returnObject;
        } catch (MalformedURLException e) {
            System.out.println("生成URL对象失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("jsoup 爬取超时");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject searchBooks(String str, int page) {
        if(str==null||str.equals("")){
            return null;
        }
        if(page==0){
            page=1;
        }
        //进行爬取查找
        try {
            Document document=Jsoup.connect(URL+"/searchbook.php?keyword="+str+"&page="+page)
                    .headers(getHeaders())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(8000)
                    .get();
            if(document==null){
                return null;
            }
            //开始解析分析
            JSONObject returnObject=new JSONObject();
            //搜索到的图书结果页数数量,进行字符串拆分
            String[] pageArr=document.body().select("#pagestats").first().text().split("/");
            //System.out.println("查询成功，搜索到页数"+pageArr[1]);
            returnObject.put("page",pageArr[0]);
            returnObject.put("allPage",pageArr[1]);
            Elements elements=document.body().select("#hotcontent .item");
            ArrayList<SearchBookInfo> arrayList=new ArrayList<>();
            for (Element ele:elements) {
                SearchBookInfo searchBookInfo=new SearchBookInfo();
                searchBookInfo.setAuthor_name(ele.select("dl dt span").text());
                searchBookInfo.setBook_name(ele.select("dl dt a").text());
                searchBookInfo.setBook_url(ele.select("dl dt a").attr("href"));
                searchBookInfo.setBook_about(ele.select("dl dd").text());
                searchBookInfo.setImg_url(ele.select(".image a img").attr("src"));
                //System.out.println(searchBookInfo.toString());
                arrayList.add(searchBookInfo);
            }
            returnObject.put("data",arrayList);
            return returnObject;
        } catch (MalformedURLException e) {
            System.out.println("生成URL对象失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("jsoup 爬取超时");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public JSONObject getChapter(String chapterUrl) {
        return null;
    }

    @Override
    public String getBookImg(String imgUrl) {
        if (imgUrl==null||imgUrl.equals("")){
            return null;
        }
        Connection.Response response = null;
        try {
            response = Jsoup.connect(URL+imgUrl)
                    .method(Connection.Method.GET)
                    .headers(getHeaders())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(8000)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response==null){
            return null;
        }
        BufferedInputStream bufferedInputStream = response.bodyStream();
        byte[] buffer = new byte[1024];
        //实际读取的长度
        int readLenghth;
        //根据文件保存地址，创建文件输出流
        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();

        //创建的一个写出的缓冲流;
        try {
            //文件逐步写入本地//先读出来，保存在buffer数组中
            while ((readLenghth = bufferedInputStream.read(buffer,0,1024)) != -1){
                fileOutputStream.write(buffer,0,readLenghth);
            }
            //关闭缓冲流
            bufferedInputStream.close();
            byte[] bytes=fileOutputStream.toByteArray();
            BASE64Encoder encoder=new BASE64Encoder();
            String str=encoder.encode(bytes);
            fileOutputStream.close();
            return "data:image/jpeg;base64,"+str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map getHeaders(){
        Map<String, String> header = new HashMap<String, String>();
        header.put("authority","www.biqudu.net");
        header.put("method","GET");
        header.put("scheme","https");
        header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        header.put("cookie", "obj=1");
        header.put("accept-encoding", "gzip, deflate, br");
        header.put("sec-fetch-dest","document");
        header.put("sec-fetch-mode","navigate");
        header.put("sec-fetch-site","same-origin");
        header.put("sec-fetch-user","?1");
        header.put("upgrade-insecure-requests","1");
        return header;
    }
}
