package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DoctorResponse {
    @Expose
    @SerializedName("data")
    private List<Doctor> data;

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

    public List<Doctor> getData() {
        return data;
    }

    public static class Doctor{

        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("h_id")
        private Long hId;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("sex")
        private String sex;

        @Expose
        @SerializedName("sort")
        private String sort;

        @Expose
        @SerializedName("intro")
        private String intro;

        @Expose
        @SerializedName("jobTime")
        private Integer jobTime;

        @Expose
        @SerializedName("count")
        private Integer count;


        public Long getId() {
            return id;
        }

        public Long gethId() {
            return hId;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public String getSort() {
            return sort;
        }

        public String getIntro() {
            return intro;
        }

        public Integer getJobTime() {
            return jobTime;
        }

        public Integer getCount() {
            return count;
        }
    }
}
