package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.for_j.HalfCalendarFragment;
import com.example.for_j.R;
import com.example.for_j.ToDoFragment;

import java.text.ParseException;

public class CalDeleteCheckDialog extends Dialog {
    private CalDeleteCheckDialog.CalDeleteCheckDialogListener calDeleteCheckDialogListener;

    public interface CalDeleteCheckDialogListener {
        void IsPositive(int isPositive) throws ParseException;
    }
    private Context context;
    public CalDeleteCheckDialog(@NonNull Context context, CalDeleteCheckDialog.CalDeleteCheckDialogListener calDeleteCheckDialogListener){
        super(context);
        this.context = context;
        this.calDeleteCheckDialogListener = calDeleteCheckDialogListener;
    }

    private HalfCalendarFragment parentFragment;

    public void setParentFragment(HalfCalendarFragment parentFragment){
        this.parentFragment = parentFragment;
    }

    private LinearLayout CDCD_layout;
    private TextView deleteMessage;
    private Button positiveBtn;
    private Button negativeBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_check_dialog);

        CDCD_layout = findViewById(R.id.HDCD_layout);
        deleteMessage = findViewById(R.id.deleteMessage);

        positiveBtn = findViewById(R.id.positiveBtn);
        negativeBtn = findViewById(R.id.negativeBtn);

        String message = null;
        message = "삭제하면 복구가 불가능합니다. 삭제하시겠습니까?";
        deleteMessage.setText(message);


        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    calDeleteCheckDialogListener.IsPositive(1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (parentFragment != null){
                    parentFragment.onResume();
                }
                dismiss();
            }
        });

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    calDeleteCheckDialogListener.IsPositive(0);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });
    }
}
