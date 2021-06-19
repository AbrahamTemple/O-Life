package com.example.myapplication.domain;

import java.io.Serializable;
import java.util.Date;

import retrofit2.http.Field;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.6.13
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
public class RegisterDto implements Serializable {

    private Long Id;

    private Long UId;

    private Long HId;

    private Long EId;

    private String Name;

    private String Sort;

    private String Username;

    private String Address;

    private Long Time;

    private String State;

    public RegisterDto(Long id, Long UId, Long HId, Long EId, String name, String sort, String username, String address, Long time, String state) {
        Id = id;
        this.UId = UId;
        this.HId = HId;
        this.EId = EId;
        Name = name;
        Sort = sort;
        Username = username;
        Address = address;
        Time = time;
        State = state;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUId() {
        return UId;
    }

    public void setUId(Long UId) {
        this.UId = UId;
    }

    public Long getHId() {
        return HId;
    }

    public void setHId(Long HId) {
        this.HId = HId;
    }

    public Long getEId() {
        return EId;
    }

    public void setEId(Long EId) {
        this.EId = EId;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
