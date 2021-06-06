package com.example.myapplication.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.15
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
public class MsgDto implements Serializable {
    /**
     * 用户组
     */
    private List<String> persons;
    /**
     * 消息
     */
    private String msg;

    private Boolean ism; // 是否为重要消息发送者

    public MsgDto(List<String> persons, String msg, Boolean ism) {
        this.persons = persons;
        this.msg = msg;
        this.ism = ism;
    }

    public List<String> getPersons() {
        return persons;
    }

    public void setPersons(List<String> persons) {
        this.persons = persons;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getIsm() {
        return ism;
    }

    public void setIsm(Boolean ism) {
        this.ism = ism;
    }
}
