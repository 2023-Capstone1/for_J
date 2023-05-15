package com.example.for_j;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Profile extends AppCompatActivity {

    Button Profile_Logout;
    TextView User_Info,User_Name, User_Id, User_Pw, User_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User_Info = findViewById(R.id.user_info);
        User_Name = findViewById(R.id.user_name);
        User_Id = findViewById(R.id.user_id);
        User_Pw = findViewById(R.id.user_pw);
        User_Email = findViewById(R.id.user_email);

        // url 작성
        ApiService InfoApiService = new ApiService();
        String url = "http://203.250.133.162:8080/usersAPI/user_info/" + "123";
        InfoApiService.getUrl(url);

        String Id = InfoApiService.getValue("user_id");
        String Pw = InfoApiService.getValue("user_pw");
        String Name = InfoApiService.getValue("user_name");
        String Email = InfoApiService.getValue("user_email");

        // 비밀번호 길이만큼 * 문자 저장
        String maskedPw;

        // 이름 사이에 * 찍는 문자 저장
        String modifiedName;

        if (Name.length() == 3) {
            modifiedName = Name.charAt(0) + "*" + Name.charAt(2);
        } else if (Name.length() == 4) {
            modifiedName = Name.charAt(0) + "**" + Name.charAt(3);
        } else {
            modifiedName = Name;
        }

        maskedPw = new String(new char[Pw.length()]).replace("\0", "*");

        User_Info.setText(modifiedName + "님의 정보");
        User_Id.setText(Id);
        User_Pw.setText(maskedPw);
        User_Email.setText(Email);
        User_Name.setText(modifiedName);

        Button setting = findViewById(R.id.profile_secession);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileSecessionPw.class);
                startActivity(intent);
            }
        });

        ImageButton profile_back = findViewById(R.id.profile_back);
        profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button profile_pw_change = findViewById(R.id.profile_pw_change);
        profile_pw_change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), LoginPwChange.class);
                startActivity(intent);
            }
        });

        Profile_Logout = findViewById(R.id.profile_logout);
        Profile_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}