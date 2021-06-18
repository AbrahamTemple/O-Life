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

    private Long Time;

    private String State;

    public RegisterDto(Long id, Long UId, Long HId, Long EId, Long time, String state) {
        this.Id = id;
        this.UId = UId;
        this.HId = HId;
        this.EId = EId;
        this.Time = time;
        this.State = state;
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

    //    public RegisterDto(Long id, Long uId, Long hId, Long eId, Long time, String state) {
//        this.id = id;
//        this.uId = uId;
//        this.hId = hId;
//        this.eId = eId;
//        this.time = time;
//        this.state = state;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getuId() {
//        return uId;
//    }
//
//    public void setuId(Long uId) {
//        this.uId = uId;
//    }
//
//    public Long gethId() {
//        return hId;
//    }
//
//    public void sethId(Long hId) {
//        this.hId = hId;
//    }
//
//    public Long geteId() {
//        return eId;
//    }
//
//    public void seteId(Long eId) {
//        this.eId = eId;
//    }
//
//
//    public Long getTime() {
//        return time;
//    }
//
//    public void setTime(Long time) {
//        this.time = time;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
}
