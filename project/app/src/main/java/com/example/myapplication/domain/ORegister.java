package com.example.myapplication.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Abraham
 * @since 2021-05-18
 */
public class ORegister implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long uId;

    private Long hId;

    private Long eId;

    private String time;

    public ORegister(Long uId, Long hId, Long eId, String time) {
        this.uId = uId;
        this.hId = hId;
        this.eId = eId;
        this.time = time;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long gethId() {
        return hId;
    }

    public void sethId(Long hId) {
        this.hId = hId;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
