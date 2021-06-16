package com.example.readbookbackground.mapper;

import com.example.readbookbackground.enums.BookInfo;
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

    @Insert("Insert into favorite_info (account_id,book_id) VALUES (#{accountId},#{bookId})")
    int insertFavoriteBook(@Param("accountId")int accountId,@Param("bookId")int bookId);

    @Select("SELECT * FROM book_info where book_id = #{bookId}")
    BookInfo getBookInfo(@Param("bookId")int bookId);

    @Select("SELECT * FROM book_info where book_name=#{bookName} AND author_name=#{book_authorName} AND source_name=#{source_name}")
    BookInfo checkBookInfo(@Param("bookName")String bookName,@Param("book_authorName")String bookAuthorName,@Param("source_name")String source_name);

    @Select("SELECT * FROM book_info where book_name like #{bookName} limit #{page_start} #{count}")
    ArrayList<BookInfo> selectBook(@Param("bookName")String str,@Param("page_start")int page_start,@Param("count")int count);

    @Insert("Insert into chapter_info (book_id,chapter_name,chapter_url) VALUES (#{bookId},#{chapterName},#{chapterPath})")
    int insertChapter(int bookId, String chapterName, String chapterPath);

    @Update("UPDATE book_info SET book_chapter_sum=book_chapter_sum+#{sum}, book_near_chapter=#{near_chapter_name} where book_id=#{book_id}")
    int updateChapterSum(int book_id, int sum, String near_chapter_name);
}
