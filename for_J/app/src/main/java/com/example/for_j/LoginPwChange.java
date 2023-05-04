package com.example.for_j;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class LoginPwChange extends AppCompatActivity {
    EditText LoginPwChange_Edit_Id, LoginPwChange_Edit_New_Pw, LoginPwChange_Edit_New_Pw_Check;
    AppCompatButton LoginPwChange_Check_Button;
    ImageButton LoginPwChange_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pw_change);

        //뒤로 가기 버튼
        LoginPwChange_Back = findViewById(R.id.LoginPwChange_back);
        LoginPwChange_Back.setOnClickListener(v -> onBackPressed() );

        LoginPwChange_Edit_Id = findViewById(R.id.LoginPwChange_edit_id);
        LoginPwChange_Edit_New_Pw = findViewById(R.id.LoginPwChange_edit_new_pw);
        LoginPwChange_Edit_New_Pw_Check = findViewById(R.id.LoginPwChange_edit_new_pw_check);
        LoginPwChange_Check_Button = findViewById(R.id.LoginPwChange_check_button);

        //비밀번호 변경 버튼 눌렀을때
        LoginPwChange_Check_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputId = LoginPwChange_Edit_Id.getText().toString();
                String input_New_pw = LoginPwChange_Edit_New_Pw.getText().toString();
                String input_New_pw_check = LoginPwChange_Edit_New_Pw_Check.getText().toString();

                // url 작성
                String url = "http://203.250.133.162:8080/usersAPI/pw_update/" + inputId +  "/" + input_New_pw + "/" + input_New_pw_check;
                ApiService LoginPwChangeApiService = new ApiService();
                LoginPwChangeApiService.putUrl(url);

                if(input_New_pw.equals(input_New_pw_check) && LoginPwChangeApiService.getStatus() == 200){
                    Toast.makeText(LoginPwChange.this, "비밀번호 변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPwChange.this, Menu.class);
                    startActivity(intent);
                }else if(!input_New_pw.equals(input_New_pw_check) && LoginPwChangeApiService.getStatus() == 200){
                    Toast.makeText(LoginPwChange.this, "새로운 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginPwChange.this, "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // 로그인 화면으로 이동
        startActivity(new Intent(LoginPwChange.this, Login.class));
        // 현재 액티비티 종료
        finish();
    }
}