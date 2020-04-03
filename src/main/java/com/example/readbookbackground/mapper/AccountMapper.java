package com.example.readbookbackground.mapper;

import com.example.readbookbackground.enums.AccountInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface AccountMapper {

    @Insert("Insert into accountInfo(account_name,account_password) VALUES (#{account_name},#{account_password})")
    int insertUser(@Param("account_name")String name,@Param("account_password")String password);

    @Select("SELECT * FROM accountInfo where account_name = #{accountName}")
    AccountInfo selectUser(@Param("accountName")String name);

    @Update("UPDATE accountInfo SET account_name=#{account_name} , account_password=#{account_password} WHERE account_id = #{account_id}")
    int updateUser(AccountInfo user);



}
