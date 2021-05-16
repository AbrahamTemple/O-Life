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

public class MsgDTO implements Serializable {
    /**
     * 用户组
     */
    private List<String> persons;
    /**
     * 消息
     */
    private String msg;

    public MsgDTO(List<String> persons, String msg) {
        this.persons = persons;
        this.msg = msg;
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
}
