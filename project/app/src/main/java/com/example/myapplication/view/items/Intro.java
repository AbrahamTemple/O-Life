package com.example.myapplication.view.items;

public class Intro {
    private int img;
    private String title;
    private String about;
    private String other;

    public Intro(int img, String title, String about, String other) {
        this.img = img;
        this.title = title;
        this.about = about;
        this.other = other;
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
}
