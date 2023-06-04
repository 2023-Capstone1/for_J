package com.example.for_j;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    Button Main_Logout;
    ImageButton Main_Logout_Btn;
    ImageButton Main_Back_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        IdSave idSave = (IdSave) getApplication();

        //뒤로 가기 버튼
        Main_Back_Button = findViewById(R.id.main_back_button);
        Main_Back_Button.setOnClickListener(v -> onBackPressed() );

        Button profile = findViewById(R.id.main_profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        Button setting = findViewById(R.id.main_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });

        Main_Logout = findViewById(R.id.main_logout);
        Main_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSave.clearData();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        Main_Logout_Btn = findViewById(R.id.main_logout_Btn);
        Main_Logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSave.clearData();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        /*int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);*/
    }
}