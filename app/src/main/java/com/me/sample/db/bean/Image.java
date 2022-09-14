package com.me.sample.db.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "image")
public class Image {

    @PrimaryKey
     @NonNull
    private String fullName;
    private String url;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
	public Image(){}
    
    @Ignore
	public Image(String fullName, String url) {
        this.fullName = fullName;
        this.url = url;
    }
}
