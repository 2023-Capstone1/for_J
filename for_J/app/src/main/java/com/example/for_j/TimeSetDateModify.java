// 앱 실행 시 저장된 값 불러오는 거
package com.example.for_j;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.for_j.dialog.DatePickerFragment;
import com.example.for_j.dialog.TimePickCategoryDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

// public class TimeSetDateModify extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener {
public class TimeSetDateModify extends AppCompatActivity  {

    // 타임트래커 타이틀
    private EditText TRSDN_Title;
    // 타임트래커 카테고리 버튼
    private Button TRSDN_CategortBtn;
    // 타임트래커 nfc 사용 유무
    private SwitchCompat TRSDN_NFCSwitch;
    // 타임트래커 nfc 등록 버튼
    private Button TRSDN_NFCBtn;
    // 타임트래커 취소 버튼
    private AppCompatButton TRSDN_Cancle;
    // 타임트래커 저장 버튼
    private AppCompatButton TRSDN_Save;

    // 타임트래커 스키마 변수
    private String listId = null;
    private String loginID = null;
    private String name = null;
    private String today = null;
    private String caName = null;
    //private String caColor = null;
    private String nfc = null;
    private final int isTodo = 0;

    private ApiService timeApiService = new ApiService();
    private ApiService timeCateApiService = new ApiService();
    private String url;
    Drawable btnDrawable;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tracker_set_date_new);

        // 타이틀바 텍스트 색상 지정
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(Html.fromHtml("<font color='#D9EAF5'>CapstoneNewEditWidget</font>"));  // @colors/blue_white랑 같은색

        // 이전 인텐트에서 받아온 값 읽어오기
        listId = getIntent().getStringExtra("id");

        loginID = "123"; // 이거 나중에 다시 설정해야함!!!!!! 임의로 넣은 값임!!!!!
        url = "http://203.250.133.162:8080/timeAPI/get_time_to_update/" + loginID + "/" + listId;
        timeApiService.getUrl(url);


        // 타임 타이틀 xml 연동
        TRSDN_Title = findViewById(R.id.TRSDN_Title);
        TRSDN_Title.setText(name);

        // 카테고리 버튼 xml 연동
        TRSDN_CategortBtn = findViewById(R.id.TRSDN_CategoryBtn);
        TRSDN_CategortBtn.setText(timeApiService.getValue("time_cName"));
        TRSDN_CategortBtn.setTypeface(null, Typeface.BOLD);

        caName = timeApiService.getValue("time_cName");
        // get_time_category_by_name API로 읽어오기
        url = "http://203.250.133.162:8080/categoryAPI/get_time_category/" + loginID + "/" + caName + "/" + isTodo;
        timeCateApiService.getUrl(url);
        String Color = timeCateApiService.getValue("time_category_color");
        int colorValue;
        switch (Color) {
            case "pink":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_pink_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_pink);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "crimson":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_crimson_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_crimson);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "orange":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_orange_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_orange);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "yellow":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_yellow_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_yellow);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "light_green":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_light_green_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_light_green);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "turquoise":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_turquoise_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_turquoise);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "pastel_blue":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_pastel_blue_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_pastel_blue);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
            case "pastel_purple":
                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_pastel_purple_selector);
                TRSDN_CategortBtn.setBackground(btnDrawable);
                colorValue = getColor(R.color.lighter_pastel_purple);
                TRSDN_CategortBtn.setTextColor(colorValue);
                break;
        }

        // 타임 카테고리 다이얼로그 클릭
        TRSDN_CategortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                TimePickCategoryDialog PCD = new TimePickCategoryDialog(com.example.for_j.TimeSetDateModify.this, new TimePickCategoryDialog.PickCategoryDialogListener() {
                    @Override
                    public void getCategoryData(String cName, String cColor) {
                        // 카테고리 색, 이름 가지고 오기
                        //timeSchemaClass.setCName(cName);
                        //timeSchemaClass.setCColor(cColor);

                        TRSDN_CategortBtn.setText(cName);
                        //caName = cName;
                        //caColor = cColor;
                        // 컬러설정
                        int colorValue;
                        switch (cColor) {
                            case "pink":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_pink_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pink);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "crimson":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_crimson_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_crimson);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "orange":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_orange_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_orange);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "yellow":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_yellow_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_yellow);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "light_green":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_light_green_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_light_green);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "turquoise":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_turquoise_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_turquoise);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "pastel_blue":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_pastel_blue_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pastel_blue);
                                TRSDN_CategortBtn.setTextColor(colorValue);
                                break;
                            case "pastel_purple":
                                btnDrawable = ContextCompat.getDrawable(com.example.for_j.TimeSetDateModify.this, R.drawable.category_pastel_purple_selector);
                                TRSDN_CategortBtn.setBackground(btnDrawable);
                                colorValue = getColor(R.color.lighter_pastel_purple);
                                TRSDN_CategortBtn.setTextColor(colorValue);
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

        // nfc switch
        TRSDN_NFCSwitch = findViewById(R.id.TRSDN_NFCSwitch);


        // nfc 인텐트 연결
        TRSDN_NFCBtn = findViewById(R.id.TRSDN_NFCBtn);
        TRSDN_NFCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RNFCIntent = new Intent(TimeSetDateModify.this, TimeRegisterNFC.class);
                startActivity(RNFCIntent);
            }
        });

        // 저장 버튼
        TRSDN_Save = findViewById(R.id.TRSDN_Save);
        TRSDN_Save.setOnClickListener(new View.OnClickListener() {
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

                // 타임 이름
                if (TRSDN_Title.getText() != null){
                    name = TRSDN_Title.getText().toString();
                }

                // 타임 카테고리 이름
                if (TRSDN_CategortBtn.getText() != null){
                    caName = TRSDN_CategortBtn.getText().toString();
                }


                if (name.length() == 0){
                    Toast toast = Toast.makeText(TimeSetDateModify.this, "타임 타이틀을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
//                }else if (today.length() == 0){
//                    Toast toast = Toast.makeText(TimeSetDateModify.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT);
//                    toast.show();
                }else if (caName.length() == 0){
                    Toast toast = Toast.makeText(TimeSetDateModify.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (nfc.length() == 0){
                    Toast toast = Toast.makeText(TimeSetDateModify.this, "nfc를 등록해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    url = "http://203.250.133.162:8080/timeAPI/update_time/" + loginID + "/" + listId + "/" + name + "/" + today + "/" + caName + "/" + nfc  ;
                    timeApiService.postUrl(url);
                    if (timeApiService.getStatus()==200){
                        finish();
                    }
                }
            }
        });

        // 취소 버튼
        TRSDN_Cancle = findViewById(R.id.TRSDN_Cancle);
        TRSDN_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
