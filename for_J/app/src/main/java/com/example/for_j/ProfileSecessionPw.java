package com.example.for_j;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSecessionPw extends AppCompatActivity {

    EditText Profile_Secession_Pw_Chat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_secession_pw);

        ImageButton setting = findViewById(R.id.profile_secession_pw_back_btn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });


        // CustomDialog 객체 생성
        CustomDialog dialog = new CustomDialog(ProfileSecessionPw.this);

        // url 작성
        ApiService InfoApiService = new ApiService();
        String url = "http://203.250.133.162:8080/usersAPI/user_info/" + "123";
        InfoApiService.getUrl(url);

        String Pw = InfoApiService.getValue("user_pw");

        Profile_Secession_Pw_Chat = findViewById(R.id.profile_secession_pw_chat);
        Profile_Secession_Pw_Chat.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    String enteredPw = Profile_Secession_Pw_Chat.getText().toString();

                    if (enteredPw.equals(Pw)) {
                        Intent intent = new Intent(getApplicationContext(), ProfileSecessionWd.class);
                        startActivity(intent);
                    } else {
                        // 메시지 설정
                        String message = "비밀번호가 다릅니다.";
                        dialog.setMessage(message);

                        // 다이얼로그 보여주기
                        dialog.show();

                    }
                    return true;
                }
                return false;
            }
        });
    }
}

