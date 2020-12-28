package com.example.todoList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>   {

    private List<Task> tasks;
    private TaskItemEventListener taskItemEventListener;

    public TaskAdapter(List<Task> tasks , TaskItemEventListener taskItemEventListener){
        this.tasks = tasks;
        this.taskItemEventListener = taskItemEventListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.setTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private View delete;
        public ViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            delete = view.findViewById(R.id.delete_item);


        }

        public void setTask(Task task) {
            checkBox.setChecked(task.isCompleted());
            checkBox.setText(task.getTitle());

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskItemEventListener.onDeleteButtonClick(task);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    taskItemEventListener.onLongClickListener(task);
                    return false;
                }
            });
        }


    }
    public void addNewItem(Task task){
        tasks.add(0 ,task);
        notifyItemChanged(0);
    }

    public void deleteItem(Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId() == task.getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void updateItem(Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if(task.getId() == tasks.get(i).getId()){
                tasks.set(i,task);
                notifyItemChanged(i);
            }
        }
    }



    interface TaskItemEventListener{
        void onDeleteButtonClick(Task task);
        void onLongClickListener(Task task);
    }
}
