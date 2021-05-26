package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.service.APPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class APPController {
    private final APPService appService;

    @Autowired
    public APPController(APPService appService) {
        this.appService = appService;
    }

    @ResponseBody
    @RequestMapping("/getVersionInfo")
    public JSONObject GetVersionInfo() {
        return null;
    }

    @ResponseBody
    @RequestMapping("/downloadAPK")
    public JSONObject DownloadAPK() {
        return null;
    }
}
