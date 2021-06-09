package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ReserverResponse {
    @Expose
    @SerializedName("data")
    private List<Reserver> data;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("msg")
    private String msg;

    public List<Reserver> getData() {
        return data;
    }

    public static class Reserver{

        @Expose
        @SerializedName("id")
        private int id;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("time")
        private Date time;

        @Expose
        @SerializedName("address")
        private String address;

        @Expose
        @SerializedName("state")
        private String state;

        @Expose
        @SerializedName("server")
        private String server;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
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

        public String getServer() {
            return server;
        }

        public Reserver(int id, String title, Date time, String address, String state, String server) {
            this.id = id;
            this.title = title;
            this.time = time;
            this.address = address;
            this.state = state;
            this.server = server;
        }
    }

    public ReserverResponse(List<Reserver> data, Integer status, String msg) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }
}
