package com.example.myapplication.domain;



public class Order {

    private String id;
    private String title;
    private String time;
    private String info;
    private String address;
    private String state;
    private String server;

    public Order(String id, String title, String time, String info, String address, String state, String server) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.info = info;
        this.address = address;
        this.state = state;
        this.server = server;
    }

    public Order(String title, String time, String info, String address, String state, String server) {
        this.title = title;
        this.time = time;
        this.info = info;
        this.address = address;
        this.state = state;
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
