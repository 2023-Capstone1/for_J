package com.example.for_j;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class ProfileSecessionWd extends AppCompatActivity {
    ImageButton Profile_Secession_Wd_Back_Btn;
    CheckBox Profile_Secession_Wd_Check;
    Button Profile_Secession_Wd_Secession_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_secession_wd);

        Profile_Secession_Wd_Back_Btn = findViewById(R.id.profile_secession_wd_back_btn);
        Profile_Secession_Wd_Back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
        Profile_Secession_Wd_Check = findViewById(R.id.profile_secession_wd_check);
        Profile_Secession_Wd_Secession_Btn = findViewById(R.id.profile_secession_wd_secession_btn);
        Profile_Secession_Wd_Secession_Btn.setEnabled(false);
        Profile_Secession_Wd_Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Profile_Secession_Wd_Secession_Btn.setEnabled(true);
                } else {
                    Profile_Secession_Wd_Secession_Btn.setEnabled(false);
                }
            }
        });
        Profile_Secession_Wd_Secession_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileSecessionWdCheck.class);
                startActivity(intent);
            }
        });
    }
}