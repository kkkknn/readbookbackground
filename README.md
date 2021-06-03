# readbookbackground
阅读器APP项目后台端

## 项目说明
APP端：https://github.com/kkkknn/readbooks.git

后台端：https://github.com/kkkknn/readbookbackground.git

爬取脚本端：https://github.com/kkkknn/Clone_Book.git

## 项目简介
框架：springboot springmvc  mybatis

数据库：MySQL5.6 redis

## 主要功能简介

###  用户模块

#### 登录（/account/login)

​	使用MD5进行加密，账号密码验证成功后生成7天时效的token返回给APP端，并将token存储到redis数据库中有效时长7天

**方式** 

​	post

**参数：**

​	String name 用户姓名

​	String password 用户密码，MD5加密后的密文

**返回值：**

​	JSON字符串 {“code”:"xxxx","data":"xxx"}



#### 注册（/account/register）
用户名查重，无重复存储数据库中，返回结果给APP端

**方式** 

​	post

**参数：**

1. String name 用户姓名
2. String password 用户密码，MD5加密后的密文

**返回值：**

​	JSON字符串 {“code”:"xxxx","data":"xxx"}



### 图书模块
#### 根据图书名字/作者名字进行图书查找图书（/book/searchBook）

​	从图书表查询重名图书（支持模糊查询）和重名作者（支持模糊查询）

**类型**

​	post

**参数**

​	String  str 要查询的图书名/作者名

​	int page 要查询的页数

**返回值**

​	JSON字符串 {“code”:"xxxx","data":"xxx"}



#### 获取图书章节内容
​	根据章节ID及图书ID 从章节表找到相关章节路径，然后读取文件内容并返回

#### 将图书加入收藏
​	添加相关数据 返回给APP端

#### 图书移除收藏

​	移除相关数据 返回给APP端

#### 获取图书书籍页详情
​	根据图书id 从数据库获取详情， 并返回给APP端





### APP模块
1. 获取最新版本信息
2. 下载APK文件

### 爬取模块
1. 更新图书源
    每3天检索一次存储目录，对比最新文件章节名与数据库中最新章节是否一致 判断是否更新到数据库中 同时更新图书表中的最新章节
2. 更新APK
    每日检索一次APK存储目录，根据文件内容判断是否更新到数据库中