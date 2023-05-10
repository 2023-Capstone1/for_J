package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.for_j.R;

import java.text.ParseException;

public class HabitDeleteCheckDialog extends Dialog {
    private HabitDeleteCheckDialog.HabitDeleteCheckDialogListener  habitDeleteCheckDialogListener;
    public interface HabitDeleteCheckDialogListener {
        void IsPositive(int isPositive) throws ParseException;
    }
    private Context context;
    int deletePosition;
    public HabitDeleteCheckDialog(@NonNull Context context, HabitDeleteCheckDialog.HabitDeleteCheckDialogListener habitDeleteCheckDialogListener, int deletePosition){
        super(context);
        this.context = context;
        this.habitDeleteCheckDialogListener = habitDeleteCheckDialogListener;
        this.deletePosition = deletePosition;
    }

    private LinearLayout HDCD_layout;
    private TextView deleteMessage;
    private Button positiveBtn;
    private Button negativeBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_delete_check_dialog);

        HDCD_layout = findViewById(R.id.HDCD_layout);
        deleteMessage = findViewById(R.id.deleteMessage);

        positiveBtn = findViewById(R.id.positiveBtn);
        negativeBtn = findViewById(R.id.negativeBtn);

        String message = null;

        switch(deletePosition){
            case 0:
                message = "현재 날짜 해빗을 삭제합니다. 삭제하면 복구가 불가능합니다. 삭제하시겠습니까?";
                break;
            case 1:
                message = "현재 날짜 포함 이전 해빗을 삭제합니다. 삭제하면 복구가 불가능합니다. 삭제하시겠습니까?";
                break;
            case 2:
                message = "현재 날짜 포함 이후 해빗을 삭제합니다. 삭제하면 복구가 불가능합니다. 삭제하시겠습니까?";
                break;
        }
        deleteMessage.setText(message);


        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    habitDeleteCheckDialogListener.IsPositive(1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    habitDeleteCheckDialogListener.IsPositive(0);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });
    }
}
