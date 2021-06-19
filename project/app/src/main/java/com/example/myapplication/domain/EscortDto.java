package com.example.myapplication.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.6.9
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
public class EscortDto implements Serializable {

    private Long userId;
    private Long staffId;
    private String userName;
    private Long timing;
    private String address;
    private String name;
    private Long phone;
    private String command;
    private String state;

    public EscortDto(Long userId, Long staffId, String userName, Long timing, String address, String name, Long phone, String command, String state) {
        this.userId = userId;
        this.staffId = staffId;
        this.userName = userName;
        this.timing = timing;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.command = command;
        this.state = state;
    }

    public EscortDto(Long userId, Long staffId, Long timing, String address, String command, String state) {
        this.userId = userId;
        this.staffId = staffId;
        this.timing = timing;
        this.address = address;
        this.command = command;
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getTiming() {
        return timing;
    }

    public void setTiming(Long timing) {
        this.timing = timing;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
