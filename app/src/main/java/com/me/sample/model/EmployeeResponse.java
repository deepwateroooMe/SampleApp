package com.me.sample.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.me.sample.db.bean.Employee;

import javax.annotation.Generated;

/**
 * 员工访问接口返回数据实体
 * @author llw
 * @description BiYingImgResponse
 */
@Generated("jsonschema2pojo")
public class EmployeeResponse {

    @SerializedName("employees")
    @Expose
    private List<Employee> employees = null;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}