package com.example.for_j;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
    }
}