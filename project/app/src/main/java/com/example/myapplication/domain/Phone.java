package com.example.myapplication.domain;

import java.util.Date;

public class Phone {
    private String sender;
    private String phoneNum;
    private Date time;

    public Phone(String sender, String phoneNum, Date time) {
        this.sender = sender;
        this.phoneNum = phoneNum;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
