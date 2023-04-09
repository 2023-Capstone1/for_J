package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.for_j.ApiService;
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

    private RadioGroup PC_listRG;
    // 로그인 id는 나중에 class로 수정할 거임
    private String loginID;
    private int isTodo = 1;

    // 카테고리 갯수
    private int cateNumMax = 20;
    int cateNum = 0;

    // 취소 저장
    private LinearLayout addCategory;
    private AppCompatButton PC_save;

    private String[] cName;
    private String[] cColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_category);

        // 디비에서 카테고리 모든 튜플 가지고 와서 라디오 버튼 만들기
        // showCategoryLayout 안에 for문으로 라디오 버튼 만들기

        PC_listRG = findViewById(R.id.PC_listRG);

        // 로그인 아이디는 나중에 수정할 거임
        loginID = "123";


        String getCategoryUrl = "http://203.250.133.156:8080/categoryAPI/get_time_category/" + loginID + "/" + isTodo;
        ApiService todoApiService = new ApiService();
        todoApiService.getUrl(getCategoryUrl);

//        String errorMessage = todoApiService.getKey("not_id");
        RadioButton[] PC_categoryRB = new RadioButton[cateNumMax];
        cName = new String[cateNumMax];
        cColor = new String[cateNumMax];


        // 200 일 때만 카테고리 라디오 버튼 생성
        if(todoApiService.getStatus() == 200){
            // 메소드에서 todo_total 값 int형으로 가져오기   // 모든 메소드 리턴값은 string
            cateNum = Integer.parseInt(todoApiService.getValue("todo_total"));

            // todo_total 만큼 라디오 버튼 만들기

            // Set the margin of the radio button
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(30, 10, 10, 10);


            Drawable btnDrawable;
            for (int i = 0; i < cateNum; i++){
                PC_categoryRB[i] = new RadioButton(context);    // 카테고리 라디오 버튼 생성
                PC_categoryRB[i].setText(todoApiService.getValue("category_name"+i));   // db에서 받아온 이름이랑 똑같이 setText
                PC_categoryRB[i].setId(View.generateViewId());  // 아이디 생성(int 형임)

                // 크기 설정
                PC_categoryRB[i].setTextSize(20);
                PC_categoryRB[i].setPadding(30, 20, 30, 20);
                PC_categoryRB[i].setLayoutParams(layoutParams);

                // 컬러설정
                switch(todoApiService.getValue("category_color"+i)){
                    case "pink":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_pink_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "crimson":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_crimson_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "orange":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_orange_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "yellow":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_yellow_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "light_green":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_light_green_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "turquoise":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_turquoise_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "pastel_blue":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_pastel_blue_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                    case "pastel_purple":
                        btnDrawable = ContextCompat.getDrawable(context, R.drawable.category_pastel_purple_selector);
                        PC_categoryRB[i].setBackground(btnDrawable);
                        break;
                }

                PC_categoryRB[i].setButtonDrawable(null);
                PC_listRG.addView(PC_categoryRB[i]);    // 라디오 그룹에 추가

                // tmp = 카테고리 이름
                cName[i] = todoApiService.getValue("category_name"+i);

                // tmp = 카테고리 컬러
                cColor[i] = todoApiService.getValue("category_color"+i);
            }
        }

        // 추가 눌렀을 때
        addCategory = findViewById(R.id.PC_Add);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cateNum >= cateNumMax){
                    Toast toast = Toast.makeText(context, "카테고리는 20개까지만 입력 가능합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
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
