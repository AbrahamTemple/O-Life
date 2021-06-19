package com.example.myapplication.domain;

import java.util.Date;

public class Reserver {

    private String title;

    private String username;

    private String address;

    private String server;

    private String info;

    private Date time;

    private String state;

    private Integer action;

    public Reserver(String title, String username, String address, String server, String info, Date time, String state, Integer action) {
        this.title = title;
        this.username = username;
        this.address = address;
        this.server = server;
        this.info = info;
        this.time = time;
        this.state = state;
        this.action = action;
    }

    public Reserver(String title, String address, String server, String info, Date time, String state, Integer action) {
        this.title = title;
        this.address = address;
        this.server = server;
        this.info = info;
        this.time = time;
        this.state = state;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
