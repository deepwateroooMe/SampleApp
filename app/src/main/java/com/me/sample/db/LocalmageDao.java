package com.me.sample.db;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalmageDao implements ImageDao {

    private final ImageDao mImageDao;

    public LocalmageDao(ImageDao mImageDao) {
        this.mImageDao = mImageDao;
    }

    @Override
        public Flowable<Employee> getEmployees() {
        return mImageDao.getEmployees();
    }

    @Override
        public Completable insertOrUpdateEmployee(Employee employee) {
        return mImageDao.insertOrUpdateEmployee(employee);
    }

//    @Override
//        public void deleteAllEmployees() {
//        mEmployeeDao.deleteAllEmployees();
//    }
}
