package com.example.todoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "table_task";

    public SQLiteHelper(@Nullable Context context ) {
        super(context, "db_app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,isCompeleted BOOLEAN);");
        }catch (SQLiteException e){
            Log.e(TAG, "onCreate: " + e.toString() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title" , task.getTitle());
        contentValues.put("isCompeleted" , task.isCompleted());
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<Task> getTasks(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME , null);
        List<Task> tasks = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            }while (cursor.moveToNext());
        }

        sqLiteDatabase.close();
        return tasks;
    }

    public void getTaskByID(int id){

    }
    public void search(String title){

    }

    public int deleteTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result =sqLiteDatabase.delete(TABLE_NAME,"id = ?",new String[]{
                String.valueOf(task.getId())
        });
        return result;
    }

    public int updateTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title" , task.getTitle());
        contentValues.put("isCompeleted" , task.isCompleted());
        int id = sqLiteDatabase.update(TABLE_NAME,contentValues,"id = ?",new String[]{
                String.valueOf(task.getId())
        });
        sqLiteDatabase.close();
        return id;
    }
}
