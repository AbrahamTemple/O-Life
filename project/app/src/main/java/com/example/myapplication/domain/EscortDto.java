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
    private Long timing;
    private String address;
    private String command;
    private String state;

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
}
