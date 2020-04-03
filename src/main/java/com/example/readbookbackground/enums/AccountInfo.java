package com.example.readbookbackground.enums;


public class AccountInfo {
    private int account_id;
    private String account_name;
    private String account_password;
    private boolean account_islogin;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }

    public boolean isAccount_islogin() {
        return account_islogin;
    }

    public void setAccount_islogin(boolean account_islogin) {
        this.account_islogin = account_islogin;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "account_id=" + account_id +
                ", account_name='" + account_name + '\'' +
                ", account_password='" + account_password + '\'' +
                ", account_islogin=" + account_islogin +
                '}';
    }
}
