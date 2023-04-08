package com.example.for_j;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import com.example.for_j.dialog.DatePickerFragment;
import com.example.for_j.dialog.PickCategoryDialog;

import java.util.Calendar;
import java.util.Locale;

public class TodoSetDateNew extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener{

    // 투두 타이틀
    private EditText TSDN_Title;
    // 투두 날짜
    private Button TSDN_DateBtn;
    // 날짜 저장 변수
    private Calendar selectedDate = Calendar.getInstance();
    // 카테고리 버튼
    private Button TSDN_CategortBtn;
    // 반복주기 버튼
    //private Button TSDN_RepeatBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_set_date_new);

        // 타이틀바 텍스트 색상 지정
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(Html.fromHtml("<font color='#D9EAF5'>CapstoneNewEditWidget</font>"));  // @colors/blue_white랑 같은색


        // 투두 타이틀 xml 연동
        TSDN_Title = findViewById(R.id.TSDN_Title);
        // 투두 날짜 xml 연동
        TSDN_DateBtn = findViewById(R.id.TSDN_DateBtn);



        // 날짜 버튼 클릭
        TSDN_DateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 날짜 가져오기
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                // Pass the selected date to the DatePickerFragment
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment TSDN_DateDialog = new DatePickerFragment(selectedDate, TodoSetDateNew.this);
                TSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });


        // 카테고리 버튼 xml 연동
        TSDN_CategortBtn = findViewById(R.id.TSDN_CategoryBtn);
        // 타임 트래커 카테고리 다이얼로그 클릭
        TSDN_CategortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                PickCategoryDialog PCD = new PickCategoryDialog(TodoSetDateNew.this, new PickCategoryDialog.PickCategoryDialogListener() {
                    @Override
                    public void getCategoryData(String cName, String cColor) {
                        // 카테고리 색, 이름 가지고 오기
















                    }
                });

                PCD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                PCD.setCanceledOnTouchOutside(true);
                PCD.setCancelable(true);
                PCD.show();
            }
        });


        /*
        // 반복 주기 버튼 xml 연동
        TSDN_RepeatBtn = findViewById(R.id.TSDN_RepeatBtn);

        // 반복 주기 버튼 클릭
        TSDN_RepeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                RepeatCycle RCD = new RepeatCycle();

                RCD.show(getSupportFragmentManager(), "RepeatCycle");
            }
        });

         */



    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        // Update the button text
        // 선택된 날짜로 데이트 피커 업데이트
        String dateString = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, day);
        TSDN_DateBtn.setText(dateString);

    }

}

