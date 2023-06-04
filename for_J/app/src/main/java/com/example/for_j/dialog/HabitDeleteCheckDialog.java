package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.for_j.HabitFragment;
import com.example.for_j.HalfCalendarFragment;
import com.example.for_j.R;

import java.text.ParseException;

public class HabitDeleteCheckDialog extends Dialog {
    private final HabitDeleteCheckDialog.HabitDeleteCheckDialogListener  habitDeleteCheckDialogListener;
    public interface HabitDeleteCheckDialogListener {
        void IsPositive(int isPositive) throws ParseException;
    }

    int deletePosition;
    public HabitDeleteCheckDialog(@NonNull Context context, HabitDeleteCheckDialog.HabitDeleteCheckDialogListener habitDeleteCheckDialogListener, int deletePosition){
        super(context);
        this.habitDeleteCheckDialogListener = habitDeleteCheckDialogListener;
        this.deletePosition = deletePosition;
    }

    private HabitFragment ParentFragment = null;
    private HalfCalendarFragment HCParentFragment = null;

    public void setParentFragment(HabitFragment parentFragment){
        this.ParentFragment = parentFragment;
    }

    public void setParentFragment(HalfCalendarFragment parentFragment){
        this.HCParentFragment = parentFragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_check_dialog);

        TextView deleteMessage = findViewById(R.id.deleteMessage);

        Button positiveBtn = findViewById(R.id.positiveBtn);
        Button negativeBtn = findViewById(R.id.negativeBtn);

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
            case 3:
                message = "해빗을 삭제합니다. 삭제하면 복구가 불가능합니다. 삭제하시겠습니까?";

        }
        deleteMessage.setText(message);


        positiveBtn.setOnClickListener(view -> {
            try {
                habitDeleteCheckDialogListener.IsPositive(1);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (ParentFragment != null){
                ParentFragment.onResume();
            }
            if (HCParentFragment != null){
                HCParentFragment.onResume();
            }

            dismiss();
        });

        negativeBtn.setOnClickListener(view -> {
            try {
                habitDeleteCheckDialogListener.IsPositive(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dismiss();
        });
    }
}
