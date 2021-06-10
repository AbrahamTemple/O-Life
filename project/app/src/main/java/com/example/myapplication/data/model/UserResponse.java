package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @Expose
    @SerializedName("data")
    private User data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public User getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public static class User{

        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("username")
        private String username;

        @Expose
        @SerializedName("password")
        private String password;

        @Expose
        @SerializedName("clientId")
        private String clientId;

        @Expose
        @SerializedName("sex")
        private String sex;

        @Expose
        @SerializedName("identify")
        private String identify;

        @Expose
        @SerializedName("address")
        private String address;

        @Expose
        @SerializedName("phone")
        private Long phone;

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getClientId() {
            return clientId;
        }

        public String getSex() {
            return sex;
        }

        public String getIdentify() {
            return identify;
        }

        public String getAddress() {
            return address;
        }

        public Long getPhone() {
            return phone;
        }
    }
}
