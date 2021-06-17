package com.example.readbookbackground.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.enums.BookInfo;
import com.example.readbookbackground.enums.ChapterInfo;
import com.example.readbookbackground.enums.FavoriteInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BookMapper {
    @Insert("Insert into book_info" +
            "(book_name,book_img_url,author_name,book_url,book_near_chapter,book_chapter_sum,book_about,source_name)" +
            " VALUES " +
            "(#{book_name},#{book_img_url},#{author_name},#{book_url},#{book_near_chapter_name},#{book_chapter_sum},#{book_about},#{source_name})")
    @Options(useGeneratedKeys = true,keyProperty = "book_id",keyColumn = "book_id")
    int insertBook(BookInfo info);

    @Insert("Insert into favorite_info (account_id,book_id) VALUES (#{account_id},#{book_id})")
    @Options(useGeneratedKeys = true,keyProperty = "favorite_id",keyColumn = "favorite_id")
    int insertFavoriteBook(FavoriteInfo favoriteInfo);

    @Select("SELECT * FROM book_info where book_id = #{bookId}")
    BookInfo getBookInfo(@Param("bookId")int bookId);

    @Select("SELECT * FROM book_info where book_name=#{bookName} AND author_name=#{book_authorName} AND source_name=#{source_name}")
    BookInfo checkBookInfo(@Param("bookName")String bookName,@Param("book_authorName")String bookAuthorName,@Param("source_name")String source_name);

    @Select("SELECT * FROM book_info where book_name like #{str} or author_name like #{str} limit #{page_index},#{count}")
    ArrayList<BookInfo> selectBook(@Param("str")String str,@Param("page_index")int page_index,@Param("count")int count);

    @Insert("Insert into chapter_info (book_id,chapter_name,chapter_url) VALUES (#{bookId},#{chapterName},#{chapterPath})")
    int insertChapter(@Param("bookId")int bookId, @Param("chapterName")String chapterName, @Param("chapterPath")String chapterPath);

    @Update("UPDATE book_info SET book_chapter_sum=book_chapter_sum+#{sum}, book_near_chapter=#{near_chapter_name} where book_id=#{book_id}")
    int updateChapterSum(@Param("book_id")int book_id, @Param("sum")int sum, @Param("near_chapter_name")String near_chapter_name);

    @Select("select * from chapter_info where book_id=#{bookId} limit #{pageIndex},#{pageSize}")
    ArrayList<ChapterInfo> getChapterList(@Param("bookId")int bookId, @Param("pageIndex")int pageIndex, @Param("pageSize")int pageSize);

    @Delete("delete from favorite_info where favorite_id=#{favorite_id}")
    int deleteFavoriteBook(@Param("favorite_id")int favorite_id);

    @Select("select * from favorite_info where account_id=#{account_id} and book_id=#{book_id}")
    Integer checkFavoriteBook(FavoriteInfo favoriteInfo);
}
