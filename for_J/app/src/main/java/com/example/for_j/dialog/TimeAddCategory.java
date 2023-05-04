package com.example.for_j.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.for_j.ApiService;
import com.example.for_j.R;
import com.example.for_j.dbSchemaClass.CategorySchemaClass;

public class TimeAddCategory extends Dialog{
    private Context context;
    private ImageView AC_Close;
    private Button AC_Save;
    private EditText AC_Title;
    private Button categoryPink, categoryCrimson, categoryOrange, categoryYellow,
            categoryLightGreen, categoryTurquoise, categoryPastelBlue, categoryPastelPurple;
//    private int colorCheck;

    private CategorySchemaClass categorySchema;

    public TimeAddCategory(@NonNull Context context){
        super(context);
        this.context = context;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.add_category);

        // 서버로 보낼 값들 저장한 클래스
        categorySchema = new CategorySchemaClass();
        categorySchema.setName(null);
        categorySchema.setColor("");


        // 카테고리 이름
        AC_Title = findViewById(R.id.AddCategory_Title);

        // 색깔 버튼 8개
        categoryPink = findViewById(R.id.categoryPink);
        categoryCrimson = findViewById(R.id.categoryCrimson);
        categoryOrange = findViewById(R.id.categoryOrange);
        categoryYellow = findViewById(R.id.categoryYellow);
        categoryLightGreen = findViewById(R.id.categoryLightGreen);
        categoryTurquoise = findViewById(R.id.categoryTurquoise);
        categoryPastelBlue = findViewById(R.id.categoryPastelBlue);
        categoryPastelPurple  = findViewById(R.id.categoryPastelPurple);


        // 클릭된 버튼에 체크 표시 하기
        // 핑크 버튼 체크 표시
        categoryPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 0;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryPink.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("pink");

                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 크림슨 버튼 체크 표시
        categoryCrimson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 1;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("crimson");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 오렌지 버튼 체크 표시
        categoryOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 2;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("orange");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 옐로우 버튼 체크 표시
        categoryYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 3;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("yellow");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 라이트 버튼 체크 표시
        categoryLightGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 4;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("light_green");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 터키즈 버튼 체크 표시
        categoryTurquoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 5;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("turquoise");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 파스텔 블루 버튼 체크 표시
        categoryPastelBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                colorCheck = 6;
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("pastel_blue");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        // 파스텔 퍼플 버튼 체크 표시
        categoryPastelPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                categoryPastelPurple.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                categorySchema.setColor("pastel_purple");

                categoryPink.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryCrimson.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryOrange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryYellow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryLightGreen.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryTurquoise.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                categoryPastelBlue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });




        // x 버튼
        AC_Close = findViewById(R.id.AC_Close);
        // 저장 버튼
        AC_Save = findViewById(R.id.AC_Save);

        // x 버튼 클릭 시 다이얼로그 종료
        AC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전 화면으로 이동
                dismiss();
            }
        });


        // 저장 버튼 누를 시
        AC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 타임트래커, 투두 각각 따로 구현
                // 카테고리 db에 이름, 색상 저장하기
                // loginId는 123으로 통일
                // 나중에 바꿀거임 여기 수정해야함!!!!!!!!!!!!!!
                categorySchema.setLoginID("123");

                // 카테고리 db에 이름, 색상 저장하기
                if (AC_Title.getText() == null){
                    categorySchema.setName(null);
                }else{
                    categorySchema.setName(AC_Title.getText().toString());
                }

                // 투두임
                categorySchema.setIsTodo(0);

                if (categorySchema.getName().length() == 0){
                    Toast toast = Toast.makeText(context, "카테고리 이름을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (categorySchema.getColor().length() == 0){
                    Toast toast = Toast.makeText(context, "카테고리 색상을 선택하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    // url 작성
                    String url = "http://203.250.133.162:8080/categoryAPI/set_time_category/" + categorySchema.getLoginID() + "/" + categorySchema.getName() + "/" + categorySchema.getColor() + "/" + categorySchema.getIsTodo();
                    ApiService categoryApiService = new ApiService();
                    categoryApiService.postUrl(url);

                    if (categoryApiService.getStatus() == 200) {
                        dismiss();
                    }
                }
            }
        });


    }
}
