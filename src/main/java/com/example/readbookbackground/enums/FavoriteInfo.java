package com.example.readbookbackground.enums;

import java.sql.Timestamp;

public class FavoriteInfo {
    private int favorite_id;
    private int book_id;
    private int account_id;
    private boolean is_save;
    private Timestamp update_time;

    public boolean isIs_save() {
        return is_save;
    }

    public void setIs_save(boolean is_save) {
        this.is_save = is_save;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "FavoriteInfo{" +
                "favorite_id=" + favorite_id +
                ", book_id=" + book_id +
                ", account_id=" + account_id +
                ", is_save=" + is_save +
                ", update_time=" + update_time +
                '}';
    }
}
