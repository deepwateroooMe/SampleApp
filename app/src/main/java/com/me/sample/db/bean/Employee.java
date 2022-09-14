package com.me.sample.db.bean;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "employee")
public class Employee {

    @NonNull
    @PrimaryKey
    private String uuid;
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private String biography;
    private String photoUrlSmall;
    private String photoUrlLarge;
    private String team;
    private String employeeType;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getBiography() {
        return biography;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }
    public String getPhotoUrlSmall() {
        return photoUrlSmall;
    }
    public void setPhotoUrlSmall(String photoUrlSmall) {
        this.photoUrlSmall = photoUrlSmall;
    }
    public String getPhotoUrlLarge() {
        return photoUrlLarge;
    }
    public void setPhotoUrlLarge(String photoUrlLarge) {
        this.photoUrlLarge = photoUrlLarge;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public String getEmployeeType() {
        return employeeType;
    }
    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
}