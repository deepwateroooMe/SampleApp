package com.me.sample.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.me.sample.db.bean.Employee;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 壁纸数据操作
 * @author llw
 */
@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    Flowable<List<Employee>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Employee> employees);

    @Query("DELETE FROM employee")
    Completable deleteAll();
}
