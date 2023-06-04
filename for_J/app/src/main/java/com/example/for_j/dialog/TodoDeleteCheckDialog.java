package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.for_j.R;
import com.example.for_j.ToDoFragment;

import java.text.ParseException;

public class TodoDeleteCheckDialog extends Dialog {
    private final TodoDeleteCheckDialog.TodoDeleteCheckDialogListener  todoDeleteCheckDialogListener;


    public interface TodoDeleteCheckDialogListener {
        void IsPositive(int isPositive) throws ParseException;
    }

    public TodoDeleteCheckDialog(@NonNull Context context, TodoDeleteCheckDialog.TodoDeleteCheckDialogListener todoDeleteCheckDialogListener){
        super(context);
        this.todoDeleteCheckDialogListener = todoDeleteCheckDialogListener;
    }

    private ToDoFragment parentFragment;

    public void setParentFragment(ToDoFragment parentFragment){
        this.parentFragment = parentFragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_check_dialog);
        TextView deleteMessage = findViewById(R.id.deleteMessage);

        Button positiveBtn = findViewById(R.id.positiveBtn);
        Button negativeBtn = findViewById(R.id.negativeBtn);

        String message;
        message = "삭제하면 복구가 불가능합니다. 삭제하시겠습니까?";
        deleteMessage.setText(message);


        positiveBtn.setOnClickListener(view -> {
            try {
                todoDeleteCheckDialogListener.IsPositive(1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (parentFragment != null){
                parentFragment.onResume();
            }
            dismiss();
        });

        negativeBtn.setOnClickListener(view -> {
            try {
                todoDeleteCheckDialogListener.IsPositive(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dismiss();
        });
    }
}
