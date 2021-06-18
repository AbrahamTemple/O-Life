package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class RegisterResponse {
    @Expose
    @SerializedName("data")
    private List<Register> data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public List<Register> getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public static class Register{

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("sort")
        private String sort;

        @Expose
        @SerializedName("time")
        private Date time;

        @Expose
        @SerializedName("address")
        private String address;

        @Expose
        @SerializedName("state")
        private String state;


        public String getName() {
            return name;
        }

        public String getSort() {
            return sort;
        }

        public Date getTime() {
            return time;
        }

        public String getAddress() {
            return address;
        }

        public String getState() {
            return state;
        }
    }
}
