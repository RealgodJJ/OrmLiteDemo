package reagodjj.example.com.ormlitedemo.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "student_db")
public class Student {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name", dataType = DataType.STRING, canBeNull = false)
    private String name;
    @DatabaseField
    private int age;
    @DatabaseField
    private String phone;
    @DatabaseField(columnName = "school_id", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private School school;

    public Student(String name, int age, String phone, School school) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.school = school;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}
