package com.me.sample.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "employee") // 表名注解
public class Employee {

    @NonNull
    @PrimaryKey
    // @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "name")
    String name;

    // 这里对于图片的存储格式可能不对
    @ColumnInfo(name = "img")
    String img;

    @Ignore
    public Employee (String name, String img) { // imgUrl
        this.name = name; 
        this.img = img;
    }
    
    @Ignore
    public Employee (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // for debugging convenience only
    // @Override
    // public String toString() {
    //     return "Emperor{" +
    //         "id=" + id +
    //         ", name='" + name + '\'' +
    //         ", age='" + age + '\'' +
    //         ", gender='" + gender + '\'' +
    //         '}';
    // }
}


// public class NicePlace {
//     private String title;
//     private String imageUrl;

//     public NicePlace(String imageUrl, String title) {
//         this.title = title;
//         this.imageUrl = imageUrl;
//     }
//     public NicePlace() {
//     }

//     public String getTitle() {
//         return title;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }

//     public String getImageUrl() {
//         return imageUrl;
//     }

//     public void setImageUrl(String imageUrl) {
//         this.imageUrl = imageUrl;
//     }

// }