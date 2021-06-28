package com.example.readbookbackground.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
                        System.out.println("666"+file.toPath());
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
                            String[] values=stringBuilder.toString().replace("\"","").split(",");
                            System.out.println(stringBuilder.toString());
                            for (String str:values) {
                                System.out.println(str);
                                String[] arr=str.split(":");
                                for (int i = 0; i < arr.length; i+=2) {
                                    switch (arr[i]){
                                        case "book_name":
                                            bookInfo.setBook_name(arr[i+1]);
                                            System.out.println(1111+arr[i+1]);
                                            break;
                                        case "author_name":
                                            bookInfo.setAuthor_name(arr[i+1]);
                                            System.out.println(222222222+arr[i+1]);
                                            break;
                                        case "source_name":
                                            bookInfo.setSource_name(arr[i+1]);
                                            System.out.println(33333+arr[i+1]);
                                            break;
                                        case "near_chapter_name":
                                            //此处读取的info.json文件，并不是实际保存的最新章节，所以不写入
                                            //bookInfo.setBook_near_chapter(StringUtil.unicodeToCn(arr[i+1]));
                                            break;
                                        case "book_about":
                                            bookInfo.setBook_about(arr[i+1]);
                                            System.out.println(444444+arr[i+1]);
                                            break;
                                        default:
                                            System.out.println("没找到");
                                            break;
                                    }
                                }
                            }
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
        System.out.println(bookInfo);
        return bookInfo;
    }

    /**
     * 获取图书章节列表
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
            //章节文件排序
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    long diff=o1.lastModified()-o2.lastModified();
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
    public static String getChapterContent(String filePath){
        File file=new File(filePath);
        if(!file.exists()){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();

        BufferedReader bufferedReader=null;
        try {
            bufferedReader=new BufferedReader(new FileReader(file));
            String temp_string=null;
            int line=-1;
            while ((temp_string=bufferedReader.readLine())!=null){
                stringBuffer.append(temp_string);

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
        return stringBuffer.toString();
    }


}
