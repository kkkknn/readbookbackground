package com.example.readbookbackground.enums;

public class FavoriteInfo {
    private int favorite_id;
    private int book_id;
    private int account_id;

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
                '}';
    }
}
