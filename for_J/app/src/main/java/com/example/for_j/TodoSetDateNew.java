package com.example.for_j;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.example.for_j.dialog.DatePickerFragment;
import com.example.for_j.dialog.TodoPickCategoryDialog;

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
    // 취소 버튼
    private AppCompatButton TSDN_Cancle;
    // 저장 버튼
    private AppCompatButton TSDN_Save;

    // 투두 스키마 변수
    private String loginID = null;
    private String name = null;
    private String date = null;
    private String caName = null;
    //private String caColor = null;
    private int state = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_set_date_new);

        IdSave idSave = (IdSave) getApplication();
        loginID = idSave.getUserId();


        // 투두 타이틀 xml 연동
        TSDN_Title = findViewById(R.id.TSDN_Title);
        // 투두 날짜 xml 연동
        TSDN_DateBtn = findViewById(R.id.TSDN_DateBtn);



        // 날짜 버튼 클릭
        TSDN_DateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 날짜 가져오기
//                int year = selectedDate.get(Calendar.YEAR);
//                int month = selectedDate.get(Calendar.MONTH);
//                int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                // Pass the selected date to the DatePickerFragment
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment TSDN_DateDialog = new DatePickerFragment(selectedDate, TodoSetDateNew.this);
                TSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });


        // 카테고리 버튼 xml 연동
        TSDN_CategortBtn = findViewById(R.id.TSDN_CategoryBtn);
        // 투두 카테고리 다이얼로그 클릭
        TSDN_CategortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                TodoPickCategoryDialog PCD = new TodoPickCategoryDialog(TodoSetDateNew.this, new TodoPickCategoryDialog.PickCategoryDialogListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void getCategoryData(String cName, String cColor) {
                        // 카테고리 색, 이름 가지고 오기
                        //todoSchemaClass.setCName(cName);
                        //todoSchemaClass.setCColor(cColor);

                        TSDN_CategortBtn.setText(cName);
                        TSDN_CategortBtn.setTypeface(null, Typeface.BOLD);
                        // 텍스트 색상
                        int colorValue;
                        Drawable btnDrawable;
                        //caName = cName;
                        //caColor = cColor;
                        // 컬러설정
                        switch(cColor){
                            case "pink":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_pink_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pink);
                                TSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "crimson":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_crimson_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_crimson);
                                TSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "orange":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_orange_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_orange);
                                TSDN_CategortBtn.setTextColor(colorValue);
                            case "yellow":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_yellow_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_yellow);
                                TSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "light_green":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_light_green_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_light_green);
                                TSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "turquoise":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_turquoise_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_turquoise);
                                TSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "pastel_blue":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_pastel_blue_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pastel_blue);
                                TSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "pastel_purple":
                                btnDrawable = ContextCompat.getDrawable(TodoSetDateNew.this, R.drawable.category_pastel_purple_selector);
                                TSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pastel_purple);
                                TSDN_CategortBtn.setTextColor(colorValue);
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


        // 저장 버튼
        TSDN_Save = findViewById(R.id.TSDN_Save);
        TSDN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    private String loginID = null;
                    private String name = null;
                    private String date = null;
                    private String caName = null;
                    private String caColor = null;
                    private int state = 0;
                 */

                // loginId는 123으로 통일
                // 나중에 바꿀거임 여기 수정해야함!!!!!!!
                loginID = "123";

                // 투두 이름
                if (TSDN_Title.getText() != null){
                    name = TSDN_Title.getText().toString();
                }

                // 투두 날짜
                if (date == null){
                    date = "";
                }

                // 투두 카테고리 이름
                if (TSDN_CategortBtn.getText() != null){
                    caName = TSDN_CategortBtn.getText().toString();
                }


                if (name.length() == 0){
                    Toast toast = Toast.makeText(TodoSetDateNew.this, "투두 타이틀을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (date.length() == 0){
                    Toast toast = Toast.makeText(TodoSetDateNew.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (caName.length() == 0){
                    Toast toast = Toast.makeText(TodoSetDateNew.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    ApiService todoApiService = new ApiService();
                    String url = "http://203.250.133.162:8080/todoAPI/set_todo/" + loginID + "/" + name + "/" + date + "/" + caName + "/" + state;
                    todoApiService.postUrl(url);

                    if (todoApiService.getStatus()==200){
                        finish();
                    }
                }
            }
        });

        // 취소 버튼
        TSDN_Cancle = findViewById(R.id.TSDN_Cancle);
        TSDN_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        date = TSDN_DateBtn.getText().toString();
    }

}

