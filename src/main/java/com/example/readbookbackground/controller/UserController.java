package com.example.readbookbackground.controller;

import com.example.readbookbackground.enums.AccountInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    AccountService accountService;

    @ResponseBody
    @RequestMapping("/Test")
    public String Test(){
        if(accountService.userRegister("张三","123456")){
            return "注册成功";
        }else{
            return "注册失败";
        }
    }

    @ResponseBody
    @RequestMapping("/Test2")
    public String Test2(){
        AccountInfo info=new AccountInfo();
        info.setAccount_name("张三");
        info.setAccount_password("123456");
        if(accountService.userLogin(info)){
            return "成功";
        }else{
            return "失败";
        }
    }

}
