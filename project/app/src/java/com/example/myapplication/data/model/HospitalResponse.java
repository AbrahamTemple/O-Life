package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalResponse {
    @Expose
    @SerializedName("data")
    private List<Hospital> data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public List<Hospital> getData() {
        return data;
    }

    public static class Hospital{

        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("address")
        private String address;

        @Expose
        @SerializedName("phone")
        private Long phone;

        @Expose
        @SerializedName("intro")
        private String intro;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public Long getPhone() {
            return phone;
        }

        public String getIntro() {
            return intro;
        }
    }
}
