package com.example.for_j.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.for_j.ApiService;
import com.example.for_j.IdSave;
import com.example.for_j.R;
import com.example.for_j.dbSchemaClass.CategorySchemaClass;

public class TimeAddCategory extends Dialog{
    private final Context context;
    private EditText AC_Title;

    private CategorySchemaClass categorySchema;

    private String user_id;


    public TimeAddCategory(@NonNull Context context){
        super(context);
        this.context = context;
    }

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.add_category);

        IdSave idSave = (IdSave) context.getApplicationContext();
        user_id = idSave.getUserId();

        // 서버로 보낼 값들 저장한 클래스
        categorySchema = new CategorySchemaClass();
        categorySchema.setName(null);
        categorySchema.setColor("");


        // 카테고리 이름
        AC_Title = findViewById(R.id.AddCategory_Title);

        RadioGroup AC_CateRG = findViewById(R.id.AC_CateRG);

        AC_CateRG.setOnCheckedChangeListener((radioGroup, witch) -> {
            switch (witch){
                case R.id.categoryPink:
                    categorySchema.setColor("pink");
                    break;
                case R.id.categoryCrimson:
                    categorySchema.setColor("crimson");
                    break;
                case R.id.categoryOrange:
                    categorySchema.setColor("orange");
                    break;
                case R.id.categoryYellow:
                    categorySchema.setColor("yellow");
                    break;
                case R.id.categoryLightGreen:
                    categorySchema.setColor("light_green");
                    break;
                case R.id.categoryTurquoise:
                    categorySchema.setColor("turquoise");
                    break;
                case R.id.categoryPastelBlue:
                    categorySchema.setColor("pastel_blue");
                    break;
                case R.id.categoryPastelPurple:
                    categorySchema.setColor("pastel_purple");
                    break;
            }
        });




        // x 버튼
        ImageView AC_Close = findViewById(R.id.AC_Close);
        // 저장 버튼
        Button AC_Save = findViewById(R.id.AC_Save);

        // x 버튼 클릭 시 다이얼로그 종료
        AC_Close.setOnClickListener(v -> {
            // 전 화면으로 이동
            dismiss();
        });


        // 저장 버튼 누를 시
        AC_Save.setOnClickListener(v -> {
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
                String url = "http://203.250.133.162:8080/categoryAPI/set_time_category/" + user_id + "/" + categorySchema.getName() + "/" + categorySchema.getColor() + "/" + categorySchema.getIsTodo();
                ApiService categoryApiService = new ApiService();
                categoryApiService.postUrl(url);

                if (categoryApiService.getStatus() == 200) {
                    dismiss();
                }
            }
        });


    }
}
