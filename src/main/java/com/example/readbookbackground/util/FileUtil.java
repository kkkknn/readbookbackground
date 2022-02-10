package com.example.readbookbackground.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtil {

    /**
     *获取图书详情
     */
    public static BookInfo getBookInfo(String path){
        BookInfo bookInfo=new BookInfo();
        //查找bookinfo.json文件
        File dir=new File(path);
        if(dir.isDirectory()){
            bookInfo.setBook_url(dir.getPath());
            File[] files=dir.listFiles();
            if(files!=null){
                for (File file:files) {
                    if(file.getName().endsWith(".json")){
                        //读取文件内容， 解析json字符串
                        StringBuilder stringBuilder=new StringBuilder();
                        BufferedReader  reader = null;
                        try {
                            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
                            reader=new BufferedReader(isr);
                            String tempString = null;
                            // 一次读入一行，直到读入null为文件结束
                            while ((tempString = reader.readLine()) != null) {
                                // 显示行号
                                stringBuilder.append(tempString);
                            }
                            //String s=new String(stringBuilder.toString().getBytes("GBK"),"UTF-8");
                            JSONObject jsonObject=JSONObject.parseObject(stringBuilder.toString());
                            String bookName=jsonObject.getString("book_name");
                            String authorName=jsonObject.getString("author_name");
                            String sourceName=jsonObject.getString("source_name");
                            String nearChapterName=jsonObject.getString("near_chapter_name");
                            String bookAbout=jsonObject.getString("book_about");
                            bookInfo.setBook_name(bookName);
                            bookInfo.setAuthor_name(authorName);
                            bookInfo.setSource_name(sourceName);
                            bookInfo.setBook_near_chapter(nearChapterName);
                            bookInfo.setBook_about(bookAbout);

                            reader.close();
                        } catch (FileNotFoundException e) {
                            System.out.println(file.toPath()+"文件未找到");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("IO流错误");
                            e.printStackTrace();
                        }
                    }else if(file.getName().endsWith(".jpg")||file.getName().endsWith(".png")||file.getName().endsWith(".gif")){
                        bookInfo.setBook_img_url(file.getPath());
                    }
                }
            }
        }
        return bookInfo;
    }

    /**
     * 获取图书章节列表 并按照章节名字排序
     */
    public static ArrayList<String[]> getChapterList(String bookPath){
        ArrayList<String[]> chapter_list=new ArrayList<>();
        //验证图书路径是否正确
        File dir=new File(bookPath+"/chapters");
        if(dir.isDirectory()){
            File[] files=dir.listFiles();
            if(files==null){
                return chapter_list;
            }
            //章节文件排序 ,根据分割的章节名字
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    int chapter1=Integer.parseInt(o1.getName().split("_")[0]);
                    int chapter2=Integer.parseInt(o2.getName().split("_")[0]);
                    long diff=chapter1-chapter2;
                    if(diff>0){
                        return 1;
                    }else if(diff==0){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            });
            for (File file:files) {
                //循环写入文件章节列表
                String[] arr=new String[2];
                String fileName=file.getName();
                //去除文件名称后缀
                arr[0]=fileName.substring(0,fileName.lastIndexOf("."));
                arr[1]=file.getPath();
                chapter_list.add(arr);
            }
        }
        return chapter_list;
    }

    /**
     * 从本地目录获取图书章节内容
     * @param filePath 文件的本地目录
     * @return
     */
    public static JSONArray getChapterContent(String filePath){
        File file=new File(filePath);
        if(!file.exists()){
            return null;
        }
        JSONArray jsonArray=new JSONArray();

        BufferedReader bufferedReader=null;
        try {
            bufferedReader=new BufferedReader(new FileReader(file));
            String temp_string=null;
            int line=-1;
            while ((temp_string=bufferedReader.readLine())!=null){
                jsonArray.add(temp_string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    /**
     * 获取最新APP信息
     * @param filePath APP存储目录
     * @return APP json信息字符串
     */
    public static String getNewAppInfo(String filePath){
        File file=new File(filePath+"info.json");
        if(!file.exists()){
            return null;
        }
        //按行读取json，并整合为json字符串
        BufferedReader bufferedReader=null;
        StringBuilder stringBuilder=new StringBuilder();
        try {
            bufferedReader=new BufferedReader(new FileReader(file));
            String temp=null;
            while ((temp=bufferedReader.readLine())!=null){
                stringBuilder.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 下载文件
     * @param response 返回的请求
     * @param urlPath  文件路径
     */
    public static void downloadFile(HttpServletResponse response, String urlPath){
        File f = new File(urlPath);
        if (!f.exists()) {
            try {
                response.sendError(404, "File not found!");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Length", ""+f.length());
        InputStream in = null;
        OutputStream out = null;
        try {
            //获取要下载的文件输入流
            in = new FileInputStream(urlPath);
            int len = 0;
            //创建数据缓冲区
            byte[] buffer = new byte[1024];
            //通过response对象获取outputStream流
            out = response.getOutputStream();
            //将FileInputStream流写入到buffer缓冲区
            while((len = in.read(buffer)) > 0) {
                //使用OutputStream将缓冲区的数据输出到浏览器
                out.write(buffer,0,len);
            }
            //这一步走完，将文件传入OutputStream中后，页面就会弹出下载框
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
                if(in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
