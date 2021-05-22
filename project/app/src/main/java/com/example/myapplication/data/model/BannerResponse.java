package com.example.myapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResponse {

    @Expose
    @SerializedName("data")
    private List<Banner> data;

    @Expose
    @SerializedName("errorCode")
    private Integer errorCode;

    @Expose
    @SerializedName("errorMsg")
    private String errorMsg;

    public List<Banner> getData() {
        return data;
    }

    public static class Banner{

        @Expose
        @SerializedName("desc")
        private String desc;

        @Expose
        @SerializedName("id")
        private int id;

        @Expose
        @SerializedName("imagePath")
        private String imagePath;

        @Expose
        @SerializedName("isVisible")
        private int isVisible;

        @Expose
        @SerializedName("order")
        private int order;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("type")
        private int type;

        @Expose
        @SerializedName("url")
        private String url;

        public String getDesc() {
            return desc;
        }

        public int getId() {
            return id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public int getIsVisible() {
            return isVisible;
        }

        public int getOrder() {
            return order;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }
    }


}
