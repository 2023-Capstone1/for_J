package com.example.for_j;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class PwChange extends AppCompatActivity {
    EditText PwChange_Edit_Id, PwChange_Edit_Now_Pw, PwChange_Edit_New_Pw, PwChange_Edit_New_Pw_Check;
    AppCompatButton PwChange_Check_Button;
    ImageButton PwChange_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);

        //뒤로 가기 버튼
        PwChange_Back = findViewById(R.id.PwChange_back);
        PwChange_Back.setOnClickListener(v -> onBackPressed() );

        PwChange_Edit_Id = findViewById(R.id.PwChange_edit_id);
        PwChange_Edit_Now_Pw = findViewById(R.id.PwChange_edit_now_pw);
        PwChange_Edit_New_Pw = findViewById(R.id.PwChange_edit_new_pw);
        PwChange_Edit_New_Pw_Check = findViewById(R.id.PwChange_edit_new_pw_check);
        PwChange_Check_Button = findViewById(R.id.PwChange_check_button);

        //비밀번호 변경 버튼 눌렀을때
        PwChange_Check_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputId = PwChange_Edit_Id.getText().toString();
                String input_Now_Pw = PwChange_Edit_Now_Pw.getText().toString();
                String input_New_pw = PwChange_Edit_New_Pw.getText().toString();

                // url 작성
                String url = "http://203.250.133.162:8080/android/pw_update/" + inputId + "/" +  input_Now_Pw + "/" + input_New_pw;
                ApiService pwchangeApiService = new ApiService();
                pwchangeApiService.postUrl(url);

                if(!input_New_pw.equals(input_Now_Pw) && pwchangeApiService.getStatus() == 200){
                    Toast.makeText(PwChange.this, "비밀번호 변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PwChange.this, Login.class);
                    startActivity(intent);
                }else if(input_New_pw.equals(input_Now_Pw) && pwchangeApiService.getStatus() == 200){
                    Toast.makeText(PwChange.this, "현재 비밀번호와 바꾸려는 비밀번호가 같습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PwChange.this, "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // 로그인 화면으로 이동
        startActivity(new Intent(PwChange.this, Login.class));
        // 현재 액티비티 종료
        finish();
    }
}