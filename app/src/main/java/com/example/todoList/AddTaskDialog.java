package com.example.todoList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddTaskDialog extends DialogFragment {
    private AddNewTaskCallBack addNewTaskCallBack;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addNewTaskCallBack = (AddNewTaskCallBack) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task,null,false);

        EditText etTitle = view.findViewById(R.id.et_title);
        TextInputLayout textInputEditText = view.findViewById(R.id.til_title);


        Button save  = view.findViewById(R.id.btn_dialog_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etTitle.getText().length() <= 0){
                    textInputEditText.setError("چیزی بنویسید");
                    return;
                }
                Task task = new Task();
                task.setTitle(etTitle.getText().toString());
                task.setCompleted(false);

                addNewTaskCallBack.onNewTask(task);
                dismiss();
            }
        });


        builder.setView(view);
        return builder.create();
    }

    interface AddNewTaskCallBack{
        void onNewTask(Task task);
    }

}
