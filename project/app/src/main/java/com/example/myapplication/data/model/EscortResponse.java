package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EscortResponse {
    @Expose
    @SerializedName("data")
    private List<Escort> data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public List<Escort> getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public static class Escort{

        @Expose
        @SerializedName("address")
        private String address;

        @Expose
        @SerializedName("command")
        private String command;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("phone")
        private Long phone;

        @Expose
        @SerializedName("timing")
        private Date timing;

        @Expose
        @SerializedName("userName")
        private String userName;

        @Expose
        @SerializedName("state")
        private String state;

        public String getAddress() {
            return address;
        }

        public String getCommand() {
            return command;
        }

        public String getName() {
            return name;
        }

        public Long getPhone() {
            return phone;
        }

        public Date getTiming() {
            return timing;
        }

        public String getUserName() {
            return userName;
        }

        public String getState() {
            return state;
        }
    }
}
