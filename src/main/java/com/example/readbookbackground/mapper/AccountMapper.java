package com.example.readbookbackground.mapper;

import com.example.readbookbackground.enums.AccountInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface AccountMapper {

    @Insert("Insert into account_info(account_name,account_password) VALUES (#{accountName},#{accountPassword})")
    int insertUser(@Param("accountName")String name,@Param("accountPassword")String password);

    @Select("SELECT account_id FROM account_info where account_name = #{accountName}")
    AccountInfo checkUser(@Param("accountName")String name);
    
    @Select("SELECT account_id FROM account_info where account_name = #{accountName} and account_password = #{accountPassword}")
    AccountInfo loginUser(@Param("accountName")String name,@Param("accountPassword")String password);


}
