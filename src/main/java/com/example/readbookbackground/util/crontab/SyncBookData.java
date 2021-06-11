package com.example.readbookbackground.util.crontab;

import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.util.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
@EnableScheduling
public class SyncBookData {
    private final static String path="F:\\home\\book_save_dir";
    @Scheduled(cron = "0 0 0 * * ?")
    public void syncData() {
        //todo: 定时任务脚本 每日执行一次 更新操作
        //读取本地磁盘路径 根据图书来源分几部分
        File dir=new File(path);
        if(dir.isDirectory()){
            File[] sources=dir.listFiles();
            ArrayList<File> source_list=new ArrayList<>();
            //遍历获取所有来源目录
            if(sources==null){
                System.out.println("图书来源获取为空");
                return;
            }
            for (File file :sources) {
                if(file.isDirectory()){
                    source_list.add(file);
                }
            }
            //获取目录内所有书籍目录
            for (File value : source_list) {
                File[] book_path = value.listFiles();
                if (book_path == null) {
                    continue;
                }
                for (File file : book_path) {
                    System.out.println(file.toPath().toString());
                    //获取图书信息图书名字，作者名字，最新章节名字
                    BookInfo bookInfo = FileUtil.getBookInfo(file.toPath().toString());
                    System.out.println(bookInfo);
                    ArrayList<String[]> list = FileUtil.getChapterList(bookInfo.getBook_url());
                    bookInfo.setBook_chapter_sum(list.size());
                    bookInfo.setBook_near_chapter_name(list.get(list.size()-1)[0]);

                    //接口获取图书信息

                }
            }
        }
        //从数据库查找图书是否存在，不存在创建图书并遍历写入


        //从数据库获取图书最新章节名字

        //对比文件夹内最新章节名字

        //不一致调用python脚本进行更新
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }


}
