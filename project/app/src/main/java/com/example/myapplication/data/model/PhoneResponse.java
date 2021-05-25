package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneResponse {

    @Expose
    @SerializedName("data")
    private String data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public String getData() {
        return data;
    }
}
