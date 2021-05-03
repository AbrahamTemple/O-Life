package com.example.myapplication.entity;

public class Counter {
    private int progress;
    private int tag;

    public Counter(int progress, int tag) {
        this.progress = progress;
        this.tag = tag;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
