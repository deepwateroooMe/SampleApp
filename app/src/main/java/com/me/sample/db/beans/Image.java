package com.me.sample.db.beans;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "image")
public class Image {

    @PrimaryKey
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
	public Image(){}
    
    @Ignore
	public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
