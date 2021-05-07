package com.example.myapplication.domain;

public class Users {
    private String name;
    private Integer codeName;
    private String url;

    public Users(String name, Integer codeName, String url) {
        this.name = name;
        this.codeName = codeName;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodeName() {
        return codeName;
    }

    public void setCodeName(Integer codeName) {
        this.codeName = codeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
