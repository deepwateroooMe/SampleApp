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
    
    @Query("SELECT * FROM image WHERE fullName LIKE :fullName LIMIT 1") 
    Image queryByName(String fullName);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Image> images);

    @Query("DELETE FROM image")
    Completable deleteAll();
}
