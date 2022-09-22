package com.me.sample.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.me.sample.db.bean.Image;
import com.me.sample.db.bean.Employee;

@Database(entities={Image.class,
        Employee.class
    }, version = 1, exportSchema = false) // exportSchema: 
public abstract class AppDatabase extends RoomDatabase {
    private final String TAG = "EmployeeDatabase";
    private static final String DATABASE_NAME = "mvvm_demo";

    private static volatile AppDatabase instance;
    
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) { 
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .build(); 
                }
            }
        }
        return instance;
    }
    
    public abstract ImageDao imageDao(); 
    public abstract EmployeeDao employeeDao();

    // private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
    //     @Override
    //     public void migrate(@NonNull SupportSQLiteDatabase database) {
    //         database.execSQL("CREATE TABLE tem_emperor (" + "id INTEGER PRIMARY KEY NOT NULL," +
    //                          "emperor_name TEXT," +
    //                          "age INTEGER," +
    //                          "gender TEXT)");
    //         database.execSQL("INSERT INTO tem_emperor (id,emperor_name,age,gender) SELECT id,emperor_name,age,gender FROM emperor_table");
    //         database.execSQL("DROP TABLE emperor_table");
    //         database.execSQL("ALTER TABLE tem_emperor RENAME TO emperor_table");
    //     }
    // };

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE `employee` " +
                             "(uuid STRING NOT NULL, " +
                             "fullName String NOT NULL, " +
                             "emailAddress String NOT NULL, " + 
                             "team String NOT NULL, " + 
                             "employeeType String NOT NULL, " + 
                             "PRIMARY KEY(`uuid`))");
        }
    };
}
