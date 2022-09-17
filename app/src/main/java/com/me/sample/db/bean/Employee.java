package com.me.sample.db.bean;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
@Entity(tableName = "employee")
public class Employee {

    @Override
    public String toString() {
        return "Employee: \n fullName: " + fullName + "; \n"
            + "uuid: " + uuid + "; \n"
            + "emailAddress: " + emailAddress + "; \n"
            + "team: " + team + "; \n"
            + "employeeType: " + employeeType + "; \n";
    }
    @PrimaryKey
    @NonNull
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

    public Employee() {}
    @Ignore
    public Employee(String uuid, String fullName, String emailAddress, String team, String employeeType, String photoUrlSmall) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.team = team;
        this.employeeType = employeeType;
        this.photoUrlSmall = photoUrlSmall;
    }

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