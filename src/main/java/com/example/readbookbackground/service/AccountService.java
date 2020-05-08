package com.example.readbookbackground.service;

import com.example.readbookbackground.enums.AccountInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public boolean userRegister(String username,String password){
        if(username==null||username.equals(" ")||password==null||password.equals(" ")){
            return false;
        }
        //查询是否用户名重复
        AccountInfo accountInfo=accountMapper.selectUser(username);
        if(accountInfo!=null){
            System.out.println("用户名重复");
            return false;
        }
        System.out.println("开始用户注册");
        //开始用户注册
        return accountMapper.insertUser(username,password)==1;
    }

    public boolean userLogin(AccountInfo user){
        if(user==null){
            return false;
        }
        user.setAccount_id(1);
        user.setAccount_name("李四");
        //AccountInfo accountInfo=accountMapper.selectUser(user.getAccount_name());
        System.out.println("用户登录"+user.toString());
        return accountMapper.updateUser(user)==1;
    }
}
