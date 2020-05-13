package com.example.readbookbackground.util.booksource;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.util.booksource.items.SearchBookInfo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Source2 implements BQGSourceImp {
    private final String URL="http://www.xbiquge.la";
    @Override
    public JSONObject getBookInfo(String url) {
        if(url==null||url.equals("")){
            return null;
        }
        //进行爬取查找
        try {
            Document document=Jsoup.connect(url)
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
            returnObject.put("bookSourceId",2);
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
            Elements elementsChapters=document.body().select("#list>dl>dd");
            ArrayList<String[]> arrayList=new ArrayList<>();
            for (Element ele:elementsChapters) {
                Elements item=ele.getElementsByTag("a");
                    String chapterUrl=item.attr("href");
                    String chapterName=item.text();
                    String[] chapterInfo=new String[2];
                    chapterInfo[0]=chapterName;
                    chapterInfo[1]=chapterUrl;
                    arrayList.add(chapterInfo);

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
            Document document= Jsoup.connect(URL+"/modules/article/waps.php")
                    .headers(getHeaders())
                    .data("searchkey",str)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(8000)
                    .post();
            if(document==null){
                return null;
            }
            //开始解析分析
            JSONObject returnObject=new JSONObject();
            returnObject.put("page",page);
            returnObject.put("allPage",page);
            Elements elements=document.body().select(".grid>tbody tr");
            ArrayList<SearchBookInfo> arrayList=new ArrayList<>();
            for (int i = 1; i < elements.size(); i++) {
                Element element=elements.get(i);
                SearchBookInfo searchBookInfo=new SearchBookInfo();
                Elements items=element.select("td");

                searchBookInfo.setAuthor_name(items.get(2).text());
                searchBookInfo.setBook_name(items.get(0).select("a").text());
                searchBookInfo.setBook_url(items.get(0).select("a").attr("href"));

                arrayList.add(searchBookInfo);
            }
            System.out.println("执行完成"+arrayList.size()+"|"+elements.size());
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
        if(chapterUrl==null||chapterUrl.equals("")){
            return null;
        }
        //进行爬取查找
        try {
            Document document=Jsoup.connect(URL+chapterUrl)
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
            String chapter_name=document.body().select(".bookname>h1").text();
            if(chapter_name==null||chapter_name.equals("")){
                System.out.println("查无此章节");
                return null;
            }
            returnObject.put("chapterName",chapter_name);
            String contentStr=document.body().select("#content").text();
            String adStr=document.body().select("#content>p").text();
            if(contentStr!=null&&!contentStr.isEmpty()){
                String valueStr=contentStr.replace(adStr,"");
                returnObject.put("chapterContent",valueStr);
            }
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
    public String getBookImg(String imgUrl) {
        if (imgUrl==null||imgUrl.equals("")){
            return null;
        }
        Connection.Response response = null;
        try {
            response = Jsoup.connect(imgUrl)
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
        header.put("method","GET");
        return header;
    }
}
