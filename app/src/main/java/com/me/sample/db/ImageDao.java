package com.me.sample.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.me.sample.db.bean.Image;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image")
    List<Image> getAll();
    
    @Query("SELECT * FROM image WHERE name LIKE :name LIMIT 1") // 这里可能还需要再修改一下
    // Image queryByName(String name);
    Flowable<Image> queryByName(String name);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // void insertAll(Image... images);
    Completable insertAll(Image... images);

    @Delete
    void delete(Image image);
}
