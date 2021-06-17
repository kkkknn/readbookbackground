package com.example.readbookbackground.util.crontab;

import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.service.BookService;
import com.example.readbookbackground.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
@EnableScheduling
public class SyncBookData {
    private final BookService bookService;
    private final static String path="F:\\home\\book_save_dir";

    @Autowired
    public SyncBookData(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 每日0点定时同步本地文件及数据库
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void syncData() {
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
                    ArrayList<String[]> list = FileUtil.getChapterList(bookInfo.getBook_url());
                    bookInfo.setBook_chapter_sum(list.size());
                    System.out.println(bookInfo.toString());
                    //接口获取图书信息
                    BookInfo ret_bookInfo=bookService.checkBook(bookInfo);
                    if(ret_bookInfo!=null){
                        //已有记录，对比总章节数
                        int size=bookInfo.getBook_chapter_sum()-ret_bookInfo.getBook_chapter_sum();
                        //总章节数一致，跳过，
                        //总章节数不一致，添加相关章节路径到数据库
                        if(size>0){
                            int val=updateChapterInfo(ret_bookInfo.getBook_id(),list,ret_bookInfo.getBook_chapter_sum(),size);
                            System.out.println(val);
                            if(val>0){
                                //数据库存储完成后，更新最新章节及总章节
                                boolean flag=bookService.updateChapterSum(ret_bookInfo.getBook_id(),val,ret_bookInfo.getBook_near_chapter());
                                System.out.println("更新结果："+flag);
                            }
                        }
                    }else{
                        //未查到记录，添加图书信息到书库
                        BookInfo val_bookInfo=bookService.addBookInfo(bookInfo);
                        //循环添加相关章节到数据库
                        if(val_bookInfo!=null){
                            int sum=updateChapterInfo(val_bookInfo.getBook_id(),list,0,list.size());
                            System.out.println(sum);
                            if(sum>0){
                                //数据库存储完成后，更新最新章节及总章节
                                boolean flag=bookService.updateChapterSum(val_bookInfo.getBook_id(),sum,val_bookInfo.getBook_near_chapter());
                                System.out.println("更新结果："+flag);
                            }

                        }
                    }
                }
            }
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }


    /**
     * 添加章节信息到数据库当中
     * @param bookId        图书id
     * @param chapterList   章节信息数组
     * @param start         起始章节
     * @param size          添加章节数量
     * @return              添加成功的章节数量
     */
    private int updateChapterInfo(int bookId,ArrayList<String[]> chapterList,int start,int size){
        int update_sum=0;
        if(bookId<=0||chapterList==null||chapterList.isEmpty()){
            return update_sum;
        }
        for (int i=0;i<size;i++){
            //添加章节信息到数据库
            String name=chapterList.get(i+start)[0];
            String path=chapterList.get(i+start)[1];
            if(bookService.addChapter(bookId,name,path)){
                update_sum++;
            }
        }
        return update_sum;
    }

}
