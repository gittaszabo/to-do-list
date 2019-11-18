package gittaSz.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

public class ToDoItemDAO {

    private ToDoItemDBHelper helper;
    private SQLiteDatabase db;

    public ToDoItemDAO(Context context) {
        this.helper = new ToDoItemDBHelper(context);
    }

    public List<ToDoItem> getAllToDoItems(){
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM todoitem", null);
        cursor.moveToFirst();
        List<ToDoItem> tasks = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String priority = cursor.getString(cursor.getColumnIndex("priority"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            ToDoItem ti = new ToDoItem(id, task, date, priority, status);
            tasks.add(ti);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return tasks;
    }

    public List<ToDoItem> getActiveToDos(){
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM todoitem WHERE status = 'active'", null);
        cursor.moveToFirst();
        List<ToDoItem> activeTasks = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String priority = cursor.getString(cursor.getColumnIndex("priority"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            ToDoItem ti = new ToDoItem(id, task, date, priority, status);
            activeTasks.add(ti);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return activeTasks;
    }

    public List<ToDoItem> getCompletedToDos(){
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM todoitem WHERE status = 'completed'", null);
        cursor.moveToFirst();
        List<ToDoItem> completedTasks = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String priority = cursor.getString(cursor.getColumnIndex("priority"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            ToDoItem ti = new ToDoItem(id, task, date, priority, status);
            completedTasks.add(ti);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return completedTasks;
    }

    public void deleteToDoItem(ToDoItem ti) {
        db = helper.getWritableDatabase();
        db.delete("todoitem", "_id=?", new String[]{ti.getId() + ""});
        db.close();
    }


    public void saveToDoItem(ToDoItem ti) {
        db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("task",ti.getTask());
        cv.put("date", ti.getDate());
        cv.put("priority", ti.getPriority());
        cv.put("status", ti.getStatus());
        if (ti.getId() == -1) {
            long id = db.insert("todoitem", null, cv);
            ti.setId((int) id);
        } else {
            db.update("todoitem", cv, "_id=?", new String[]{ti.getId() + ""});
        }
        db.close();
    }


    public void deleteAllToDoItems(){
        db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM todoitem");
        db.close();
    }
}
