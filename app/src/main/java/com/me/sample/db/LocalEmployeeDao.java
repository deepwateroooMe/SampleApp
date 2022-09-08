package com.me.sample.db;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalEmployeeDao implements EmployeeDao {

    private final EmployeeDao mEmployeeDao;

    public LocalEmployeeDao(EmployeeDao mEmployeeDao) {
        this.mEmployeeDao = mEmployeeDao;
    }

    @Override
        public Flowable<Employee> getEmployees() {
        return mEmployeeDao.getEmployees();
    }

    @Override
        public Completable insertOrUpdateEmployee(Employee employee) {
        return mEmployeeDao.insertOrUpdateEmployee(employee);
    }

//    @Override
//        public void deleteAllEmployees() {
//        mEmployeeDao.deleteAllEmployees();
//    }
}
