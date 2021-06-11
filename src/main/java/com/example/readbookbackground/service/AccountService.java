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

    public int userRegister(String username,String password){
        /**
         *用户名/密码 规则验证
         * 用户名 20字符限制 仅允许数字+英文组合
         * 密码  由于是MD5密文  仅需验证是否为MD5字符串即可
         */
        if(!password.matches("^[a-fA-F0-9]{32}$")||!username.matches("^[\\w]{0,10}$")){
            return 0;
        }
        //查询是否用户名重复
        AccountInfo accountInfo=accountMapper.checkUser(username);
        if(accountInfo!=null){
            System.out.println("用户名重复");
            return 0;
        }
        System.out.println("开始用户注册");
        //开始用户注册
        return accountMapper.insertUser(username,password)==1?1:-1;
    }

    public String userLogin(String username, String password){
        /**
         *用户名/密码 规则验证
         * 用户名 20字符限制 仅允许数字+英文组合
         * 密码  由于是MD5密文  仅需验证是否为MD5字符串即可
         */
        if(!password.matches("^[a-fA-F0-9]{32}$")||!username.matches("^[\\w]{0,10}$")){
            return null;
        }
        AccountInfo accountInfo=accountMapper.loginUser(username,password);
        if(accountInfo==null){
            return null;
        }
        //进行MD5加密并生成token令牌
        String token= UUID.randomUUID().toString().replace("-","");
        //在redis中保存,7天有效,以用户id为key
        String key="token_"+accountInfo.getAccount_id();
        if(redisService.set(key,token,604800)){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("accountId",accountInfo.getAccount_id());
            jsonObject.put("token",token);
            return jsonObject.toJSONString();
        }else {
            System.out.println("token 生成失败");
            return null;
        }
    }

}
