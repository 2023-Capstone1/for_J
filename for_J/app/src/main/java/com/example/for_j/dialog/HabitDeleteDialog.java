package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.for_j.R;

public class HabitDeleteDialog extends Dialog {
    private HabitDeleteDialog.HabitDeleteDialogListener  habitDeleteDialogListener;
    public interface HabitDeleteDialogListener {
        void getDeletePosition(int deletePosition);
    }
    private Context context;
    public HabitDeleteDialog(@NonNull Context context, HabitDeleteDialogListener  habitDeleteDialogListener){
        super(context);
        this.context = context;
        this.habitDeleteDialogListener = habitDeleteDialogListener;
    }

    private LinearLayout HDD_layout;

    // 현재 날짜 삭제
    private TextView deletePositionCurrent;
    // 현재 날짜 포함 이전 날짜 모두 삭제
    private TextView deletePositionPre;
    // 현재 날짜 포함 이후 날짜 모두 삭제
    private TextView deletePositionNext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_delete_dialog);

        HDD_layout = findViewById(R.id. HDD_layout);

        deletePositionCurrent = findViewById(R.id.deletePositionCurrent);
        deletePositionPre = findViewById(R.id.deletePositionPre);
        deletePositionNext = findViewById(R.id.deletePositionNext);

        deletePositionCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitDeleteDialogListener.getDeletePosition(0);
                dismiss();
            }
        });

        deletePositionPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitDeleteDialogListener.getDeletePosition(1);
                dismiss();
            }
        });

        deletePositionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitDeleteDialogListener.getDeletePosition(2);
                dismiss();
            }
        });
    }

}
