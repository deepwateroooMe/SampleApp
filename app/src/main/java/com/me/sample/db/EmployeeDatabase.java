package com.me.sample.db;

@Database(entities={employee.class}, version = 1)
public class EmployeeDatabase extends RoomDatabase {
    private final String TAG = "EmployeeDatabase";

    private static volatile EmployeeDatabase instance;
    
    public static UsersDatabase getInstance(Context context) { // 使用单例模式，双重检测机制
        if (instance == null) {
            synchronized (EmployeeDatabase.class) { // 上锁锁在这个类上
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(getApplicationContext(), EmployeeDatabase.class, "employees.db")
                        .build(); 
                }
            }
        }
        return instance;
    }
    
    public abstract EmployeeDao EmployeeDao(); // Declare your data access objects as abstract
}
