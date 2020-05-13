package com.example.readbookbackground.service;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.AccountInfo;
import com.example.readbookbackground.mapper.AccountMapper;
import com.example.readbookbackground.util.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountMapper accountMapper;
    private final RedisService redisService;

    @Autowired
    public AccountService(AccountMapper accountMapper, RedisService redisService) {
        this.accountMapper = accountMapper;
        this.redisService = redisService;
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

    public JSONObject userLogin(String name, String password){
        AccountInfo accountInfo=accountMapper.loginUser(name,password);
        JSONObject jsonObject=new JSONObject();
        if(accountInfo==null){
            jsonObject.put("code","error");
            jsonObject.put("data","登录失败，请检查用户名和密码是否正确");
            return jsonObject;
        }
        //进行MD5加密并生成token令牌
        String token= UUID.randomUUID().toString().replace("-","");
        //在redis中保存,7天有效,以用户name为key
        String key="token_"+accountInfo.getAccount_id();
        if(redisService.set(key,token,604800)){
            jsonObject.put("code","success");
            jsonObject.put("data",accountInfo);
            jsonObject.put("token",token);
        }else {
            jsonObject.put("code","error");
            jsonObject.put("data","令牌获取失败，请联系开发人员");
        }
        return jsonObject;
    }

}
