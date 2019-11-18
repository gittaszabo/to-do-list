package gittaSz.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoItemDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public ToDoItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todoitem(_id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, date TEXT, priority TEXT, status TEXT)");

        ContentValues cv = new ContentValues();

        cv.put("task", "Sample task");
        cv.put("date", "01-Jan-2019");
        cv.put("priority", "high");
        cv.put("status", "active");
        db.insert("todoitem", null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE todoitem");
        onCreate(db);

    }
}
