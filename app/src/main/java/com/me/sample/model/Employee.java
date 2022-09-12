package com.me.sample.model;

import android.util.Log;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Employee {
    private final String TAG = "Employee"; 

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    
    @SerializedName("biography")
    @Expose
    private String biography;
    
    @SerializedName("photo_url_small")
    @Expose
    private String photoUrlSmall;
    
    @SerializedName("photo_url_large")
    @Expose
    private String photoUrlLarge;
    
    @SerializedName("team")
    @Expose
    private String team;
    
    @SerializedName("employee_type")
    @Expose
    private String employeeType;

    // @Override
    // public String toString() {
    //     Log.d(TAG, "uuid: " + uuid);
    //     Log.d(TAG, "fullName: " + fullName);
    //     Log.d(TAG, "emailAddress: " + emailAddress);
    //     Log.d(TAG, "team: " + team);
    //     Log.d(TAG, "employeeType: " + employeeType);
    //     return "";
    // }

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