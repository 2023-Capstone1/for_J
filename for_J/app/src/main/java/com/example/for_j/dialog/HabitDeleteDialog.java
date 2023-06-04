package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.for_j.R;

public class HabitDeleteDialog extends Dialog {
    private final HabitDeleteDialog.HabitDeleteDialogListener  habitDeleteDialogListener;
    public interface HabitDeleteDialogListener {
        void getDeletePosition(int deletePosition);
    }

    public HabitDeleteDialog(@NonNull Context context, HabitDeleteDialogListener  habitDeleteDialogListener){
        super(context);
        this.habitDeleteDialogListener = habitDeleteDialogListener;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_delete_dialog);

        // 현재 날짜 삭제
        TextView deletePositionCurrent = findViewById(R.id.deletePositionCurrent);
        // 현재 날짜 포함 이전 날짜 모두 삭제
        TextView deletePositionPre = findViewById(R.id.deletePositionPre);
        // 현재 날짜 포함 이후 날짜 모두 삭제
        TextView deletePositionNext = findViewById(R.id.deletePositionNext);
        // 해빗 전체 삭제
        TextView deletePositionAll = findViewById(R.id.deletePositionAll);

        deletePositionCurrent.setOnClickListener(view -> {
            habitDeleteDialogListener.getDeletePosition(0);
            dismiss();
        });

        deletePositionPre.setOnClickListener(view -> {
            habitDeleteDialogListener.getDeletePosition(1);
            dismiss();
        });

        deletePositionNext.setOnClickListener(view -> {
            habitDeleteDialogListener.getDeletePosition(2);
            dismiss();
        });

        deletePositionAll.setOnClickListener(view -> {
            habitDeleteDialogListener.getDeletePosition(3);
            dismiss();
        });
    }

}
