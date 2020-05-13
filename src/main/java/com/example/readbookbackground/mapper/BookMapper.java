package com.example.readbookbackground.mapper;

import com.example.readbookbackground.enums.BookInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookMapper {
    @Insert("Insert into bookInfo" +
            "(book_name,book_img,book_update_time,book_author_name,book_url,book_chapter_count,book_source_id)" +
            " VALUES " +
            "(#{book_name},#{book_img},#{book_update_time},#{book_author_name},#{book_url},#{book_chapter_count},#{book_source_id})")
    @Options(useGeneratedKeys = true,keyProperty = "book_id",keyColumn = "book_id")
    int insertBook(BookInfo info);

    @Insert("Insert into favoriteInfo (account_id,book_id) VALUES (#{account_id},#{book_id})")
    int insertFavoriteBook(@Param("account_id")int accountId,@Param("book_id")int bookId);

    @Select("SELECT * FROM bookInfo where book_id = #{book_id}")
    BookInfo selectBookInfo(@Param("book_id")int bookId);

    @Select("SELECT * FROM bookInfo where book_name=#{book_name} AND book_author_name=#{book_author_name}  AND book_source_id=#{book_source_id} ")
    BookInfo checkBookInfo(@Param("book_name")String bookName,@Param("book_author_name")String bookAuthorName,@Param("book_source_id")int mode);
}
