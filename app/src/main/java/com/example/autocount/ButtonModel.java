package com.example.autocount;

import androidx.annotation.DrawableRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "auto_count")
public class ButtonModel {
    @PrimaryKey(autoGenerate = false)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @DrawableRes
    private int imageResource;
    @ColumnInfo(name = "totalCount")
    private int totalCount;

    public ButtonModel(String name, int imageResource, int totalCount) {
        super();
        this.name = name;
        this.imageResource = imageResource;
        this.totalCount = totalCount;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
