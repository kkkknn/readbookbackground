package com.example.readbookbackground.enums;

import java.sql.Date;

public class AppInfo {
    private int app_id;
    private String app_name;
    private String app_path;
    private String app_log;
    private Date app_update_time;

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_path() {
        return app_path;
    }

    public void setApp_path(String app_path) {
        this.app_path = app_path;
    }

    public String getApp_log() {
        return app_log;
    }

    public void setApp_log(String app_log) {
        this.app_log = app_log;
    }

    public Date getApp_update_time() {
        return app_update_time;
    }

    public void setApp_update_time(Date app_update_time) {
        this.app_update_time = app_update_time;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "app_id=" + app_id +
                ", app_name='" + app_name + '\'' +
                ", app_path='" + app_path + '\'' +
                ", app_log='" + app_log + '\'' +
                ", app_update_time=" + app_update_time +
                '}';
    }
}
