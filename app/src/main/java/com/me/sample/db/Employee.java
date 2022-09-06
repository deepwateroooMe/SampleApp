package com.me.sample.db;

@Entity(tableName = "employee") // 表名注解
public class Employee {

    @NonNull
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "userId")
    String id;

    @ColumnInfo(name = "img")
    String img;
    
    @Ignore
    public Employee (Long userId) {
        this.id = id;
    }
}
