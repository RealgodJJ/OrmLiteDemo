package reagodjj.example.com.ormlitedemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import reagodjj.example.com.ormlitedemo.entity.Student;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper databaseHelper = null;
    private static final String DB_NAME = "test.db";

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Student.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Student.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
