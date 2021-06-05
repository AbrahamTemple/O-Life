package com.example.myapplication.domain;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String name;
    private Integer reserve;
    private Integer call;
    private Integer register;
    private Integer chat;

    public UserInfo(String name, Integer reserve, Integer call, Integer register, Integer chat) {
        this.name = name;
        this.reserve = reserve;
        this.call = call;
        this.register = register;
        this.chat = chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReserve() {
        return reserve;
    }

    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    public Integer getCall() {
        return call;
    }

    public void setCall(Integer call) {
        this.call = call;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Integer getChat() {
        return chat;
    }

    public void setChat(Integer chat) {
        this.chat = chat;
    }
}
