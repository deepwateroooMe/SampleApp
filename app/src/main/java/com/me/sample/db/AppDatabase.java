package com.me.sample.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.me.sample.db.beans.Image;

@Database(entities={Image.class}, version = 1, exportSchema = true) // exportSchema: 这个是干什么用的 ？
public abstract class AppDatabase extends RoomDatabase {
    private final String TAG = "EmployeeDatabase";

    private static volatile AppDatabase instance;
    
    public static AppDatabase getInstance(Context context) { // 使用单例模式，双重检测机制
        if (instance == null) {
            synchronized (AppDatabase.class) { // 上锁锁在这个类上
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "employees.db")
                        
// 关于数据库升级迁移，以及出错出现异常时回退到某个版本的逻辑处理
                        // .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        // //为了防止数据库升级失败导致崩溃，加入该方法可以在出现异常时创建数据表而不崩溃，但表中数据会丢失
                        // .fallbackToDestructiveMigration()
                        
                        .build(); 
                }
            }
        }
        return instance;
    }
    
    public abstract ImageDao imageDao(); // Declare your data access objects as abstract


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    // 数据库迁移升级的逻辑可以补上
    // // 升级相关
    // private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    //     @Override
    //     public void migrate(@NonNull SupportSQLiteDatabase database) {
    //         //在这里做升级操作，比如增加或者减少表中的字段
    //         database.execSQL("ALTER TABLE emperor_table ADD COLUMN gender TEXT");
    //     }
    // };
    // private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
    //     @Override
    //     public void migrate(@NonNull SupportSQLiteDatabase database) {
    //         //创建临时表
    //         database.execSQL("CREATE TABLE tem_emperor (" + "id INTEGER PRIMARY KEY NOT NULL," +
    //                          "emperor_name TEXT," +
    //                          "age INTEGER," +
    //                          "gender TEXT)");
    //         //将数据导入临时表
    //         database.execSQL("INSERT INTO tem_emperor (id,emperor_name,age,gender) SELECT id,emperor_name,age,gender FROM emperor_table");
    //         //删除原表
    //         database.execSQL("DROP TABLE emperor_table");
    //         //临时表改成原表的名字
    //         database.execSQL("ALTER TABLE tem_emperor RENAME TO emperor_table");
    //     }
    // };
}
