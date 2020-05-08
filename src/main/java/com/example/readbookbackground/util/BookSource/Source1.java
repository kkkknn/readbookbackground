package com.example.readbookbackground.util.BookSource;

import com.example.readbookbackground.util.BookSource.items.SearchBookInfo;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

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
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                    .timeout(15000)
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
            Elements elements=document.body().select("#info p");
            String author_name=elements.get(0).text().split("：")[1];
            String update_time=elements.get(2).text().split("：")[1];
            String latest_chapter=elements.get(3).select("a").text();
            returnObject.put("authorName",author_name);
            returnObject.put("updateTime",update_time);
            returnObject.put("latestChapter",latest_chapter);
            //循环添加所有章节
            Elements elementsChapters=document.body().select("#list dl>*");
            boolean isStart=false;
            String tagChapter="《"+book_name+"》正文";
            ArrayList<String[]> arrayList=new ArrayList<>();
            for (Element ele:elementsChapters) {
                if(tagChapter.equals(ele.text())){
                    isStart=true;
                    continue;
                }
                if(isStart){
                    String chapterUrl=ele.getElementsByTag("a").attr("href");
                    String chapterName=ele.getElementsByTag("a").text();
                    String[] chapterInfo=new String[2];
                    chapterInfo[0]=chapterName;
                    chapterInfo[1]=chapterUrl;
                    arrayList.add(chapterInfo);
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
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                    .timeout(15000)
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
    public void getAllChapter(String bookUrl) {

    }

    @Override
    public void getChapter(String chapterUrl) {

    }



}
