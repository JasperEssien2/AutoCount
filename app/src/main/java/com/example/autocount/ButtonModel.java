package com.example.autocount;

import androidx.annotation.DrawableRes;

public class ButtonModel {
    private int id;
    private String name;
    @DrawableRes
    private int imageResource;
    private int currentCount;

    public ButtonModel(String name, int imageResource, int currentCount) {
        super();
        this.name = name;
        this.imageResource = imageResource;
        this.currentCount = currentCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getId() {
        return id;
    }
}
