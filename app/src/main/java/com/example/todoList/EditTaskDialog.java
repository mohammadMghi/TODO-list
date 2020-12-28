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

import com.google.android.material.textfield.TextInputLayout;

public class EditTaskDialog extends DialogFragment {
    private EditTaskCallBack editTaskCallBack;
    private Task task;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        task = getArguments().getParcelable("task");
        editTaskCallBack = (EditTaskCallBack) context;
        if (task == null){
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task,null,false);

        EditText etTitle = view.findViewById(R.id.et_title_edit);
        TextInputLayout textInputEditText = view.findViewById(R.id.til_title_edit);

        etTitle.setText(task.getTitle());
        Button save  = view.findViewById(R.id.btn_dialog_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etTitle.getText().length() <= 0){
                    textInputEditText.setError("چیزی بنویسید");
                    return;
                }
                task.setTitle(etTitle.getText().toString());

                editTaskCallBack.onEdit(task);
                dismiss();
            }
        });


        builder.setView(view);
        return builder.create();
    }

    interface EditTaskCallBack{
        void onEdit(Task task);
    }

}
