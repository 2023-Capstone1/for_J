package com.example.for_j;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileSecessionWdCheck extends AppCompatActivity {
    ImageButton Profile_Secession_Wd_Check_Back_Btn;
    EditText Profile_Secession_Pw_Check_Chat;
    Button Profile_Secession_Wd_Check_Btn;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_secession_wd_check);

        Profile_Secession_Wd_Check_Back_Btn = findViewById(R.id.profile_secession_wd_check_back_btn);
        Profile_Secession_Wd_Check_Back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        // CustomDialog 객체 생성
        CustomDialog dialog = new CustomDialog(ProfileSecessionWdCheck.this);

        IdSave idSave = (IdSave) getApplication();
        user_id = idSave.getUserId();

        // url 작성
        ApiService InfoApiService = new ApiService();
        String url = "http://203.250.133.162:8080/usersAPI/user_info/" + user_id;
        InfoApiService.getUrl(url);

        String Pw = InfoApiService.getValue("user_pw");

        Profile_Secession_Wd_Check_Btn = findViewById(R.id.profile_secession_wd_check_btn);
        Profile_Secession_Wd_Check_Btn.setEnabled(false);
        Profile_Secession_Pw_Check_Chat = findViewById(R.id.profile_secession_pw_check_chat);
        Profile_Secession_Pw_Check_Chat.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    String enteredPw = Profile_Secession_Pw_Check_Chat.getText().toString();

                    if (enteredPw.equals(Pw)) {
                        // 버튼 활성화
                        Profile_Secession_Wd_Check_Btn.setEnabled(true);
                        // 메시지 설정
                        String message = "탈퇴하기 버튼을 누르세요.";
                        dialog.setMessage(message);

                        // 다이얼로그 보여주기
                        dialog.show();
                    } else {
                        // 버튼 비활성화
                        Profile_Secession_Wd_Check_Btn.setEnabled(false);
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
        Profile_Secession_Wd_Check_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Pw = Profile_Secession_Pw_Check_Chat.getText().toString();

                // wd_dialog 출력
                Dialog dialog = new Dialog(ProfileSecessionWdCheck.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.wd_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button cancelButton = dialog.findViewById(R.id.no_Btn);
                Button wdButton = dialog.findViewById(R.id.wd_Btn);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                wdButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // url 작성
                        ApiService InfoApiService = new ApiService();
                        String url = "http://203.250.133.162:8080/usersAPI/user_delete/" + user_id + "/" + Pw;
                        InfoApiService.deleteUrl(url);
                        idSave.clearData();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}