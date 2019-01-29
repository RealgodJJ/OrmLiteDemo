package reagodjj.example.com.ormlitedemo;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import reagodjj.example.com.ormlitedemo.database.DatabaseHelper;
import reagodjj.example.com.ormlitedemo.entity.School;
import reagodjj.example.com.ormlitedemo.entity.Student;

public class OrmLiteTest extends InstrumentationTestCase {
    private DatabaseHelper getDatabaseHelper() {
        return DatabaseHelper.getInstance(this.getInstrumentation().getTargetContext());
    }

    private Dao<Student, Integer> getStudentDao() throws SQLException {
        return getDatabaseHelper().getDao(Student.class);
    }

    private Dao<School, Integer> getSchoolDao() throws SQLException {
        return getDatabaseHelper().getDao(School.class);
    }

    public void testInsert() throws SQLException {
        Dao<Student, Integer> studentDao = getStudentDao();
        Dao<School, Integer> schoolDao = getSchoolDao();
        School school = new School("清华大学", "北京");
        Student stu1 = new Student("MaAonan", 23, "13439127523", school);
        Student stu2 = new Student("Zhanghui", 51, "13439870873", school);
        Student stu3 = new Student("Xiaomage", 23, "13240058735", school);
        schoolDao.create(school);
        studentDao.create(stu1);
        studentDao.create(stu2);
        studentDao.create(stu3);
    }

    public void testQueryForAll() throws SQLException {
        Dao<School, Integer> schoolDao = getSchoolDao();
        List<School> schools = schoolDao.queryForAll();

        Dao<Student, Integer> studentDao = getStudentDao();
        List<Student> students = studentDao.queryForAll();

        for (Student student : students) {
            Log.i("RealgodJJ", "students: " + student.toString() + "School: " + student.getSchool());
        }

        for (School school : schools) {
            Log.i("RealgodJJ", school.toString());
            for (Student student : school.getStudents()) {
                Log.i("RealgodJJ", "School's students: " + student);
            }
        }
    }

    public void testQuery() throws SQLException {
        Dao<Student, Integer> studentDao = getStudentDao();
        Student stu1 = studentDao.queryForId(3);
        Log.i("RealgodJJ", stu1.toString());
        List<Student> studentList = studentDao.queryForEq("name", "MaAonan");
        for (Student student : studentList) {
            Log.i("RealgodJJ", student.toString());
        }

//        studentDao.queryRaw("select from student_tb where name = ?", "MaAonan");
    }

    public void testUpdate() throws SQLException {
        Dao<Student, Integer> studentDao = getStudentDao();
        UpdateBuilder updateBuilder = studentDao.updateBuilder();
        updateBuilder.setWhere(updateBuilder.where().eq("phone", "13439127523")
                .and().gt("age", 22));
        updateBuilder.updateColumnValue("name", "RealgodJJ");
        updateBuilder.updateColumnValue("age", 24);
        updateBuilder.update();
    }

    public void testDelete() throws SQLException {
        Dao<Student, Integer> studentDao = getStudentDao();
        DeleteBuilder deleteBuilder = studentDao.deleteBuilder();
        deleteBuilder.setWhere(deleteBuilder.where().eq("name", "Xiaomage")
                .and().eq("age", 23));
        deleteBuilder.delete();
    }

    public void testTransaction() {
        try {
            TransactionManager.callInTransaction(
                    getDatabaseHelper().getConnectionSource(),
                    new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            Student student = new Student("trans test", new Random().nextInt(20),
                                    "...", new School("Fuck", "Dick"));
                            Dao<Student, Integer> studentDao = getStudentDao();
                            for (int i = 0; i < 5; i++) {
                                studentDao.create(student);
//                                if (i == 2) {
//                                    throw new SQLException("test.....");
//                                }
                            }
                            return null;
                        }
                    }
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
