package com.example.readbookbackground.service;

import com.example.readbookbackground.util.FileUtil;
import org.springframework.stereotype.Service;

@Service
public class APPService {
    //存放APP目录
    private final String path="/home/clonebook_application/apk_files/";

    public String getVersionInfo(){
        //读取指定文件目录json
        return FileUtil.getNewAppInfo(path);
    }

}
