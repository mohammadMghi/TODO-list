package com.example.todoList;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getTasks();

    @Insert
    long insertNewTask(Task task);

    @Delete
    int delete(Task task);

    @Query("SELECT * FROM task WHERE title LIKE '%' || :query || '%'")
    List<Task> search(String query);

    @Query("DELETE FROM task")
    void deleteTasks();

    @Update
    int update(Task task);
}
