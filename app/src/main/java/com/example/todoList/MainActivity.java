package com.example.todoList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddNewTaskCallBack, TaskAdapter.TaskItemEventListener , EditTaskDialog.EditTaskCallBack  {
    private List<Task> tasks;
    private TaskAdapter taskAdapter;
    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteHelper = new SQLiteHelper(this);
        tasks = sqLiteHelper.getTasks();

        setRecyclerView();


        View addNewTaskFab = findViewById(R.id.add_newTask) ;
        addNewTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog taskDialog = new AddTaskDialog();
                taskDialog.show(getSupportFragmentManager(),null);
            }
        });

    }

    public void setRecyclerView(){
        taskAdapter = new TaskAdapter(tasks ,MainActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(taskAdapter);
    }


    @Override
    public void onNewTask(Task task) {
        long id = sqLiteHelper.addTask(task);
        if(id != -1){
            task.setId(id);
            taskAdapter.addNewItem(task);
        }

    }

    @Override
    public void onDeleteButtonClick(Task task) {
        int result = sqLiteHelper.deleteTask(task);
        if(result > 0){
            taskAdapter.deleteItem(task);
        }
    }

    @Override
    public void onLongClickListener(Task task) {
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task" , task);
        editTaskDialog.setArguments(bundle);
        editTaskDialog.show(getSupportFragmentManager(),null);
    }


    @Override
    public void onEdit(Task task) {
        int result = sqLiteHelper.updateTask(task);
        if(result > 0){
            taskAdapter.updateItem(task);
        }
    }
}