package com.example.for_j.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.for_j.ApiService;
import com.example.for_j.R;
import com.example.for_j.dbSchemaClass.CategorySchemaClass;

public class TodoAddCategory extends Dialog{
    private final Context context;
    private ImageView AC_Close;
    private Button AC_Save;
    private EditText AC_Title;
    private RadioGroup AC_CateRG;

    private CategorySchemaClass categorySchema;

    public TodoAddCategory(@NonNull Context context){
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

        AC_CateRG = findViewById(R.id.AC_CateRG);

        AC_CateRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int witch) {
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
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
                categorySchema.setIsTodo(1);

                if (categorySchema.getName().length() == 0){
                    Toast toast = Toast.makeText(context, "카테고리 이름을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (categorySchema.getColor().length() == 0){
                    Toast toast = Toast.makeText(context, "카테고리 색상을 선택하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    // url 작성
                    String url = "http://203.250.133.162:8080/categoryAPI/set_todo_category/" + categorySchema.getLoginID() + "/" + categorySchema.getName() + "/" + categorySchema.getColor() + "/" + categorySchema.getIsTodo();
                    ApiService categoryApiService = new ApiService();
                    categoryApiService.postUrl(url);

                    if (categoryApiService.getStatus() == 200){
                        dismiss();
                    }
                }



            }
        });


    }
}
