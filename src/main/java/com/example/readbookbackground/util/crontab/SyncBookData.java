package com.example.readbookbackground.util.crontab;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SyncBookData {
    @Scheduled(cron = "0 0 0 * * ?")
    private void syncData() {
        //todo: 定时任务脚本 每日执行一次 更新操作
        //读取本地磁盘路径 根据图书来源分几部分

        //读取各部分内容书籍名字

        //从数据库获取图书最新章节名字

        //对比文件夹内最新章节名字

        //不一致调用python脚本进行更新
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }


}
