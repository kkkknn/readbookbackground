package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.service.APPService;
import com.example.readbookbackground.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/app")
public class APPController {
    private final APPService appService;

    @Autowired
    public APPController(APPService appService) {
        this.appService = appService;
    }

    @ResponseBody
    @PostMapping("/getVersionInfo")
    public String getVersionInfo() {
        JSONObject jsonObject=new JSONObject();
        String valueStr=appService.getVersionInfo();
        if(valueStr==null||valueStr.isEmpty()){
            jsonObject.put("code","error");
            jsonObject.put("data","获取版本信息失败");
        }else {
            jsonObject.put("code","success");
            jsonObject.put("data",valueStr);
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping ("/downloadAPK")
    public void downloadAPK(HttpServletResponse response, String urlPath) {
        FileUtil.downloadFile(response, urlPath);
    }
}
