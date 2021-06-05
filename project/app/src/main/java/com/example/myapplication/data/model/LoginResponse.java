package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @Expose
    @SerializedName("data")
    private Login data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public Login getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public static class Login{
        @Expose
        @SerializedName("access_token")
        private String accessToken;

        @Expose
        @SerializedName("token_type")
        private String tokenType;

        @Expose
        @SerializedName("expires_in")
        private String expiresIn;

        @Expose
        @SerializedName("scope")
        private String scope;

        public String getAccessToken() {
            return accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public String getExpiresIn() {
            return expiresIn;
        }

        public String getScope() {
            return scope;
        }
    }
}
