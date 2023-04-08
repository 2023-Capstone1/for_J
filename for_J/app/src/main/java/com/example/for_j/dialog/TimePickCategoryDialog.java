package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.for_j.R;

public class TimePickCategoryDialog extends Dialog {

    private TimePickCategoryDialog.PickCategoryDialogListener PickCategoryDialogListener;
    public interface PickCategoryDialogListener {
        void getCategoryData(String cName, String cColor);
    }
    private Context context;
    public TimePickCategoryDialog(@NonNull Context context, PickCategoryDialogListener pickCategoryDialogListener) {
        super(context);
        this.context = context;
        this.PickCategoryDialogListener = pickCategoryDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_category);

        // 디비에서 카테고리 모든 튜플 가지고 와서 라디오 버튼 만들기
        // showCategoryLayout 안에 for문으로 라디오 버튼 만들기

        // 추가 눌렀을 때
        LinearLayout addCategory = findViewById(R.id.PC_Add);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add_category 다이얼로그 열기
                TimeAddCategory AC = new TimeAddCategory(TimePickCategoryDialog.this.getContext());
                AC.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                AC.setCanceledOnTouchOutside(true);
                AC.setCancelable(true);
                AC.show();
            }
        });

        // 카테고리 눌렀을 때 카테고리 이름, 색상 반환
        // 버튼 눌렀을 때 밑꺼 참고해서 만들기
        /*
        // 저장 버튼 누를 시
        RC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayofWeek = "";
                if (DRC_Sun.isChecked()) {
                    dayofWeek += DRC_Sun.getText().toString();
                }
                if (DRC_Mon.isChecked()) {
                    dayofWeek += DRC_Mon.getText().toString();
                }
                if (DRC_Tue.isChecked()) {
                    dayofWeek += DRC_Tue.getText().toString();
                }
                if (DRC_Wed.isChecked()) {
                    dayofWeek += DRC_Wed.getText().toString();
                }
                if (DRC_Thu.isChecked()) {
                    dayofWeek += DRC_Thu.getText().toString();
                }
                if (DRC_Fri.isChecked()) {
                    dayofWeek += DRC_Fri.getText().toString();
                }
                if (DRC_Sat.isChecked()) {
                    dayofWeek += DRC_Sat.getText().toString();
                }

                int repeatN = RC_WNRnum.getValue();
                ;

                repeatDialogListener.getRepeatData(dayofWeek, repeatN);
                dismiss();
            }
        });
         */









    }
}
