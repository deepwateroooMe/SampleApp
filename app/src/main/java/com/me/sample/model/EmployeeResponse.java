package com.me.sample.model;

import java.util.List;

/**
 * 员工访问接口返回数据实体
 * @author llw
 * @description BiYingImgResponse
 */
public class EmployeeResponse {
    private List<EmployeesBean> mEmpList;
    public List<EmployeesBean> getEmployees() {
        return mEmpList;
    }
    public void setEmployees(List<EmployeesBean> employees) {
        this.mEmpList = employees;
    }
    public static class EmployeesBean {
        private String uuid;
        private String name;
        private String phoneNumber;
        private String email;
        private String biography;
        private String imgUrl;
        private String team;
        private String hiredType;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getHiredType() {
            return hiredType;
        }

        public void setHiredType(String hiredType) {
            this.hiredType = hiredType;
        }
    }
}