package com.example.myapplication.entity;

public class Users {
    private String name;
    private Integer codeName;

    public Users(String name, Integer codeName) {
        this.name = name;
        this.codeName = codeName;
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
}
