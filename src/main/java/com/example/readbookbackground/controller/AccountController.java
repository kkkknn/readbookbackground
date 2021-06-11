package com.example.readbookbackground.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.service.AccountService;
import com.example.readbookbackground.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 主要分为以下几个接口
* 1. 登录，返回token，登录时通过拦截器进行拦截
* 2. 注册
* 3. 注销，手动删除token
* */
@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseBody
    @PostMapping("/login")
    public String Login(String accountName,String accountPassword ){
        JSONObject jsonObject=new JSONObject();
        //防止SQL注入
        if(!StringUtil.containsSqlInjection(accountName)||!StringUtil.containsSqlInjection(accountPassword)){
            jsonObject.put("code", "error");
            jsonObject.put("data", "参数错误");
        }else {
            String result=accountService.userLogin(accountName,accountPassword);
            if(result!=null&&!result.isEmpty()){
                jsonObject.put("code", "success");
                jsonObject.put("data",result);
            }else{
                jsonObject.put("code", "error");
                jsonObject.put("data", "登录错误，请检查用户名/密码是否正确");
            }
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @PostMapping("/register")
    public String Register(String accountName,String accountPassword){
        JSONObject jsonObject=new JSONObject();
        if(!StringUtil.containsSqlInjection(accountName)||!StringUtil.containsSqlInjection(accountPassword)){
            int result=accountService.userRegister(accountName,accountPassword);
            switch (result){
                case 0:
                    jsonObject.put("code","error");
                    jsonObject.put("data","用户名重复/密码不符合规范");
                    break;
                case 1:
                    jsonObject.put("code","success");
                    break;
                case -1:
                    jsonObject.put("code","error");
                    jsonObject.put("data","注册失败/请联系网站管理员");
                    break;
            }
        }else {
            jsonObject.put("code","error");
            jsonObject.put("data","参数错误");
        }
        return  jsonObject.toJSONString();
    }

}
