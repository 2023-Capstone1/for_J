package com.example.for_j;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.for_j.dialog.TimePickCategoryDialog;
import com.example.for_j.dialog.TimePickerFragment;

import java.util.Calendar;

public class TimeTrackerSetDateNew extends AppCompatActivity{

    // 타임트래커 타이틀
    private EditText TRSDN_Title;
    // 타임트래커 카테고리 버튼
    private Button TRSDN_CategoryBtn;
    // 타임트래커 nfc 등록 버튼
    private AppCompatButton TRSDN_NFCBtn;
    // 타임트래커 취소 버튼
    private AppCompatButton TRSDN_Cancle;
    // 타임트래커 저장 버튼
    private AppCompatButton TRSDN_Save;

    private String loginID = null;
    private String name = null;
    private String today = null;
    private String caName = null;
    private String caColor = null;
    private String nfc = null;
    private int state = 0;

    private Calendar todayCal = Calendar.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tracker_set_date_new);

        IdSave idSave = (IdSave) getApplication();
        loginID = idSave.getUserId();

        int year = todayCal.get(Calendar.YEAR);
        int month = todayCal.get(Calendar.MONTH) + 1; // Add 1 to the month to represent 1-12 range
        int day = todayCal.get(Calendar.DAY_OF_MONTH);

        today = String.format("%04d-%02d-%02d", year, month, day);


        // 타임 트래커 타이틀 xml 연동
        TRSDN_Title = findViewById(R.id.TRSDN_Title);

        // 타임 트래커 카테고리 버튼 xml 연동
        TRSDN_CategoryBtn = findViewById(R.id.TRSDN_CategoryBtn);
        // 타임 트래커 카테고리 다이얼로그 클릭
        TRSDN_CategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                TimePickCategoryDialog PCD = new TimePickCategoryDialog(TimeTrackerSetDateNew.this, new TimePickCategoryDialog.PickCategoryDialogListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void getCategoryData(String cName, String cColor) {
                        // 카테고리 색, 이름 가지고 오기, TodoSetDateNew.java 참고

                        TRSDN_CategoryBtn.setText(cName);
                        TRSDN_CategoryBtn.setTypeface(null, Typeface.BOLD);

                        int colorValue;
                        Drawable btnDrawable;
                        caName = cName;
//                        caColor = cColor;
                        // 컬러설정
                        switch(cColor){
                            case "pink":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_pink_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pink);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                            case "crimson":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_crimson_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_crimson);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                            case "orange":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_orange_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_orange);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                            case "yellow":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_yellow_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_yellow);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                            case "light_green":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_light_green_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_light_green);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                            case "turquoise":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_turquoise_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_turquoise);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                            case "pastel_blue":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_pastel_blue_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pastel_blue);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                            case "pastel_purple":
                                btnDrawable = ContextCompat.getDrawable(TimeTrackerSetDateNew.this, R.drawable.category_pastel_purple_selector);
                                TRSDN_CategoryBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pastel_purple);
                                TRSDN_CategoryBtn.setTextColor(colorValue);
                                break;
                        }
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
        /*TRSDN_NFCBtn = findViewById(R.id.TRSDN_NFCBtn);
        TRSDN_NFCBtn.setOnClickListener(v -> {
            Intent registerNFCIntent = new Intent(this, TimeRegisterNFC.class);
            registerNFCResultLauncher.launch(registerNFCIntent);
        });*/

        /*TRSDN_NFCBtn = findViewById(R.id.TRSDN_NFCBtn);
        TRSDN_NFCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerNFCIntent = new Intent(TimeTrackerSetDateNew.this, TimeRegisterNFC.class);
                registerNFCResultLauncher.launch(registerNFCIntent);
            }
        });*/


        // 저장 버튼
        TRSDN_Save = findViewById(R.id.TRSDN_Save);
        // 취소 버튼
        TRSDN_Cancle = findViewById(R.id.TRSDN_Cancle);


        // 저장 누르면
        TRSDN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                private String loginID = null;
//                private String name = null;
//                private String today = null;
//                private String caName = null;
//                private String caColor = null;
//                private String nfc = null;

                // 타임트래커 타이틀
//                private EditText TRSDN_Title;
//                // 타임트래커 카테고리 버튼
//                private Button TRSDN_CategoryBtn;
//                // 타임트래커 nfc 등록 버튼
//                private AppCompatButton TRSDN_NFCBtn;

                try {
                    if (TRSDN_Title.getText() != null) {
                        name = TRSDN_Title.getText().toString();
                    }
                } catch (NullPointerException e) {
                    name = "";
//                    System.out.println("제목 null exception");
                    Toast.makeText(TimeTrackerSetDateNew.this, "해빗 타이틀을 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                if (name.length() == 0) {
                    Toast.makeText(TimeTrackerSetDateNew.this, "해빗 타이틀을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (caName.length() == 0) {
                    Toast toast = Toast.makeText(TimeTrackerSetDateNew.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }/*
                else if (nfc.length() == 0){
                    // nfc 값
                    nfc = "none";}*/
                else {
                    ApiService todoApiService = new ApiService();
                    String url = "http://203.250.133.162:8080/timeAPI/set_timeTracker/" + loginID + "/" + name + "/" + today + "/" + caName;
                    todoApiService.postUrl(url);
                    System.out.println(url);


                    if (todoApiService.getStatus()==200){
                        finish();
                    }
                }
            }
        });

        // 취소 누르면
        TRSDN_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initBtn();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    public void initBtn(){
        TRSDN_CategoryBtn.setText("not selected");
        TRSDN_CategoryBtn.setTextColor(Color.WHITE);
    }

}

