package com.example.todoList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddNewTaskCallBack,
        TaskAdapter.TaskItemEventListener ,
        EditTaskDialog.EditTaskCallBack  {
    private List<Task> tasks;
    private TaskAdapter taskAdapter;
    private AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getAppDatabase(this);
        tasks = appDatabase.getTaskDao().getTasks();

        setRecyclerView();

        EditText editText = findViewById(R.id.search_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0){
                    List<Task> tasks = appDatabase.getTaskDao().search(s.toString());
                    taskAdapter.setTasks(tasks);
                }
            }
        });


        View addNewTaskFab = findViewById(R.id.add_newTask) ;
        addNewTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog taskDialog = new AddTaskDialog();
                taskDialog.show(getSupportFragmentManager(),null);
            }
        });

        View deleteAll = findViewById(R.id.delete_all);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDatabase.getTaskDao().deleteTasks();
                taskAdapter.clearItems();
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
        long id = appDatabase.getTaskDao().insertNewTask(task);
        if(id != -1){
            task.setId(id);
            taskAdapter.addNewItem(task);
        }

    }

    @Override
    public void onDeleteButtonClick(Task task) {
        int result = appDatabase.getTaskDao().delete(task);
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
    public void changeStateCheckBox(Task task) {
        int result = appDatabase.getTaskDao().update(task);
        if(result > 0){
            taskAdapter.updateItem(task);
        }
    }


    @Override
    public void onEdit(Task task) {
        int result = appDatabase.getTaskDao().update(task);
        if(result > 0){
            taskAdapter.updateItem(task);
        }
    }
}