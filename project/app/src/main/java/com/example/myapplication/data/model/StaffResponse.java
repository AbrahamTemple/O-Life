package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffResponse {
    @Expose
    @SerializedName("data")
    private List<Staff> data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<Staff> getData() {
        return data;
    }

    public static class Staff{
        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("sex")
        private String sex;

        @Expose
        @SerializedName("address")
        private String address;

        @Expose
        @SerializedName("intro")
        private String intro;

        @Expose
        @SerializedName("phone")
        private Long phone;

        @Expose
        @SerializedName("weight")
        private Integer weight;

        @Expose
        @SerializedName("jobTime")
        private Integer jobTime;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public String getAddress() {
            return address;
        }

        public String getIntro() {
            return intro;
        }

        public Long getPhone() {
            return phone;
        }

        public Integer getWeight() {
            return weight;
        }

        public Integer getJobTime() {
            return jobTime;
        }
    }
}
