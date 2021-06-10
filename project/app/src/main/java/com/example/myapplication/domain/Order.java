package com.example.myapplication.domain;

import java.util.Date;

public class Order {
    private String id;
    private String title;
    private Date time;
    private String address;
    private String state;
    private String server;

    public Order(String id, String title, Date time, String address, String state, String server) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.address = address;
        this.state = state;
        this.server = server;
    }

    public Order(String title, Date time, String address, String state, String server) {
        this.title = title;
        this.time = time;
        this.address = address;
        this.state = state;
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
