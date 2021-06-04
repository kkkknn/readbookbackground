package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.service.APPService;
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
        File f = new File(urlPath);
        if (!f.exists()) {
            try {
                response.sendError(404, "File not found!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String type = urlPath.substring(urlPath.lastIndexOf(".") + 1);
        //判断下载类型 xlsx 或 xls 现在只实现了xlsx、xls两个类型的文件下载
        if (type.equalsIgnoreCase("apk") || type.equalsIgnoreCase("xls")){
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
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
}
