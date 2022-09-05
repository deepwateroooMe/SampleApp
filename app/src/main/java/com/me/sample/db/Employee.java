package com.me.sample.db;

@Entity(tableName = "employee") // 表名注解
public class Employee {

    @NonNull
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "userId")
    Long id;

    @ColumnInfo(name = "userName")
    String name;

    @Ignore
    public Employee (Long userId) {
        this.id = id;
    }
}
