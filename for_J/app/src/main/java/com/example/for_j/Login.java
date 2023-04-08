package com.example.for_j;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextInputEditText Login_Id, Login_Pw;
    TextView Login_Button, Login_Id_Pw_Find, Login_Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_Id = findViewById(R.id.Login_id);
        Login_Pw = findViewById(R.id.Login_pw);
        Login_Button = findViewById(R.id.Login_button);
        Login_Id_Pw_Find = findViewById(R.id.Login_id_pw_find);
        Login_Signup = findViewById(R.id.Login_signup);

        // 로그인 버튼
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputId = Login_Id.getText().toString();
                String inputPw = Login_Pw.getText().toString();

                // CustomDialog 객체 생성
                CustomDialog dialog = new CustomDialog(Login.this);

                // url 작성
                String url = "http://203.250.133.156:8080/usersAPI/login/" + inputId + "/" +  inputPw;
                ApiService loginApiService = new ApiService();
                loginApiService.getUrl(url);

                String key = loginApiService.getKey("login_id_pw_error");

                if(loginApiService.getStatus() == 200){
                    Toast.makeText(Login.this, "로그인 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, CalendarMainActivity.class);
                    startActivity(intent);
                }else if(key == "login_id_pw_error") {
                    // url 만들어지고 ID 잘못될때 출력
                    // 메시지 설정
                    String message = "잘못된 아이디입니다.";
                    dialog.setMessage(message);

                    // 다이얼로그 보여주기
                    dialog.show();
                }else {
                    // url 만들어지고 PW 잘못될때 출력
                    // 메시지 설정
                    String message = "잘못된 비밀번호입니다.";
                    dialog.setMessage(message);

                    // 다이얼로그 보여주기
                    dialog.show();
                }
            }
        });

        Login_Id_Pw_Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ID/PW 찾기 화면으로 이동
                Intent intent = new Intent(Login.this, IdPwFind.class);
                startActivity(intent);
            }
        });

        Login_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}