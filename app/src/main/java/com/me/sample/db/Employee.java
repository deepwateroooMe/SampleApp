package com.me.sample.db;

@Entity(tableName = "employee") // 表名注解
public class Employee {

    @NonNull
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "userId")
    String id;

    // 这里对于图片的存储格式可能不对
    @ColumnInfo(name = "img")
    String img;

    @Ignore
    public Employee (String userId, String img) {
        this.userId = userId;
        this.img = img;
    }
    
    @Ignore
    public Employee (String userId) {
        this.id = id;
    }

    // for debugging convenience only
    // @Override
    // public String toString() {
    //     return "Emperor{" +
    //         "id=" + id +
    //         ", name='" + name + '\'' +
    //         ", age='" + age + '\'' +
    //         ", gender='" + gender + '\'' +
    //         '}';
    // }
}
