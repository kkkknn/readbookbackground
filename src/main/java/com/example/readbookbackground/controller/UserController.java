package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.AccountInfo;
import com.example.readbookbackground.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 主要分为以下几个接口
* 1. 登录，返回token，登录时通过拦截器进行拦截
* 2. 注册
* 3. 注销，手动删除token
* */
@Controller
@RequestMapping("/User")
public class UserController {
    private final AccountService accountService;

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }


    @ResponseBody
    @RequestMapping("/Login")
    public JSONObject Login(JSONObject object){
        AccountInfo info=new AccountInfo();
        info.setAccount_name(object.getString("userName"));
        info.setAccount_password(object.getString("userPassword"));
        JSONObject jsonObject=new JSONObject();
        if(accountService.userLogin(info)){
            jsonObject.put("code","success");
            jsonObject.put("data","login token");

            return jsonObject;
        }else{
            jsonObject.put("code","error");
            jsonObject.put("data","error 原因");
            return jsonObject;
        }
    }

    @ResponseBody
    @RequestMapping("/Register")
    public JSONObject Register(JSONObject object){
        String name="",password="";
        name=object.getString("userName");
        password=object.getString("userPassword");
        if(name!=null&&password!=null&&!name.equals("")&&!password.equals("")){
            boolean result=accountService.userRegister(name,password);
            JSONObject jsonObject=new JSONObject();
            if(result){
                    jsonObject.put("code","success");

                jsonObject.put("data","Register 成功");
                return jsonObject;
            }else{
                jsonObject.put("code","error");
                jsonObject.put("data","error 原因");
                return jsonObject;
            }
        }
        System.out.println("数据为空");
        return  null;
    }

}
