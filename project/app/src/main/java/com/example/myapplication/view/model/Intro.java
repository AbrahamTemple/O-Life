package com.example.myapplication.view.model;

public class Intro {
    private int img;
    private String title;
    private String about;
    private String other;
    private String main;

//    public Intro(int img, String title, String about, String other) {
//        this.img = img;
//        this.title = title;
//        this.about = about;
//        this.other = other;
//    }


    public Intro(int img, String title, String about, String other, String main) {
        this.img = img;
        this.title = title;
        this.about = about;
        this.other = other;
        this.main = main;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
