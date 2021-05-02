package com.example.myapplication.entity;

public class Users {
    private String name;
    private Long codeName;

    public Users(String name, Long codeName) {
        this.name = name;
        this.codeName = codeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCodeName() {
        return codeName;
    }

    public void setCodeName(Long codeName) {
        this.codeName = codeName;
    }
}
