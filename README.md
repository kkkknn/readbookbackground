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

~~~
	使用MD5进行加密，账号密码验证成功后生成7天时效的token返回给APP端，并将token存储到redis数据库中有效时长7天

方式	post

参数：
	String name 用户姓名
	String password 用户密码，MD5加密后的密文

返回值：	JSON字符串 {“code”:"xxxx","data":"xxx"}
~~~

#### 注册（/account/register）

~~~
	用户名查重，无重复存储数据库中，返回结果给APP端

方式	post

参数
	String name 用户姓名
	String password 用户密码，MD5加密后的密文

返回值	JSON字符串 {“code”:"xxxx","data":"xxx"}

~~~



### 图书模块
#### 根据图书名字/作者名字进行图书查找图书（/book/searchBook）

~~~
	从图书表查询重名图书（支持模糊查询）和重名作者（支持模糊查询）
类型	post
参数
	String  str		要查询的图书名/作者名
	int pageCount	要查询的页码
	int pageSize	每页的数据数量
返回值
	JSON字符串 {“code”:"xxxx","data":"xxx"}
~~~



#### 获取图书章节内容（/book/getChapterContent）

~~~
	根据章节ID及图书ID 从章节表找到相关章节路径，然后读取文件内容并返回
类型	post
参数
	int chapterPath 
返回值	
	JSON字符串

~~~

​	

#### 将图书加入收藏(/book/addFavoriteBook)

~~~
	添加相关数据 返回给APP端
类型	post
参数
	int accountId			用户id
	int bookId				图书id
返回值	
	JSON字符串
~~~



#### 图书移除收藏(/book/removeFavtoriteBook)

~~~
	移除图书收藏相关数据 返回给APP端
类型	post
参数
	int accountId			用户id
	int bookId				图书id
返回值	
	JSON字符串
~~~



#### 获取图书书籍页详情（/book/getBookInfo）

~~~
	根据图书id 从数据库获取详情， 并返回给APP端
类型	post
参数
	int bookId
返回值	
	JSON字符串
~~~



#### 获取图书章节列表(/book/getChapterList)

~~~
	根据图书ID，从数据库分页查询图书章节列表，并返回给APP端
类型	post
参数
	int bookId		图书ID
	int pageCount	页码
	int pageSize	每页数据大小
返回值	
	JSON 字符串
~~~



#### 下载图书封面图片(/book/downloadPic)

~~~
	根据图书图片URL去下载
类型	get
参数
	String URL
返回值
	下载的文件流
~~~





### APP模块

#### 获取最新版本信息

~~~
	获取最新版本APP 版本号，更新说明，下载地址
类型	post
参数
	无
返回值	
	JSON字符串
~~~

#### 下载APK文件

~~~
	要下载文件的地址
类型	get
参数
	String downloadUrl
返回值	
~~~

### 爬取模块

#### 更新图书源

~~~
无外部接口
每天定时调用python爬取脚本
~~~

#### 更新APK

~~~
无外部接口
每天定时检索一次APK存储目录，根据文件内容判断是否更新到数据库中
~~~

