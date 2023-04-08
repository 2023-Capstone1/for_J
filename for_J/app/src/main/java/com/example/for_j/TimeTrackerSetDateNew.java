package com.example.for_j;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.for_j.dialog.TimePickCategoryDialog;

public class TimeTrackerSetDateNew extends AppCompatActivity {

    private EditText TRSDN_Title;   // 타임트래커 타이틀
    private Button TRSDN_CategortBtn;   // 타임트래커 카테고리 버튼
    private Button TRSDN_Cancle, TRSDN_Save;    // 타임트래커 저장, 취소 버튼
    private Button TRSDN_NFCBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tracker_set_date_new);

        // 타이틀바 텍스트 색상 지정
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(Html.fromHtml("<font color='#D9EAF5'>CapstoneNewEditWidget</font>"));  // @colors/blue_white랑 같은색


        // 타임 트래커 타이틀 xml 연동
        TRSDN_Title = findViewById(R.id.TRSDN_Title);
        // 타임 트래커 카테고리 버튼 xml 연동
        TRSDN_CategortBtn = findViewById(R.id.TRSDN_CategoryBtn);

        // 타임 트래커 카테고리 다이얼로그 클릭
        TRSDN_CategortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                TimePickCategoryDialog PCD = new TimePickCategoryDialog(TimeTrackerSetDateNew.this, new TimePickCategoryDialog.PickCategoryDialogListener() {
                    @Override
                    public void getCategoryData(String cName, String cColor) {
                        // 카테고리 색, 이름 가지고 오기, TodoSetDateNew.java 참고










                    }
                });

                PCD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                PCD.setCanceledOnTouchOutside(true);
                PCD.setCancelable(true);
                PCD.show();
            }
        });
        // 카테고리 이름, 색상 불러와서 카테고리 버튼에 적용하기


        // nfc 인텐트 연결
        TRSDN_NFCBtn = findViewById(R.id.TRSDN_NFCBtn);
        TRSDN_NFCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RNFCIntent = new Intent(TimeTrackerSetDateNew.this, RegisterNFC.class);
                startActivity(RNFCIntent);
            }
        });

        // rNFC에서 nfc 카드값 가져오기!!!!















        // 저장 버튼
        TRSDN_Save = findViewById(R.id.TRSDN_Save);
        // 취소 버튼
        TRSDN_Cancle = findViewById(R.id.TRSDN_Cancle);


        // 저장 누르면
        TRSDN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // edit text 디비에 저장하기
                // 현재 날짜 db에 저장하기
                // 카테고리 버튼 색상, 이름 저장하기
                // nfc 값 저장하기 -> nfc 없으면 기본값은 null
                // db에 모두 올리기
                finish();
            }
        });

        // 취소 누르면
        TRSDN_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

