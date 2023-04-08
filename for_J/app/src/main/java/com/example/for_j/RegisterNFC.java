package com.example.for_j;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterNFC extends AppCompatActivity {

    Button RNFC_TagBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nfc);

        RNFC_TagBtn = (Button) findViewById(R.id.RNFC_TagBtn);

        RNFC_TagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"태그 준비가 완료되었어요. NFC를 태그해주세요!",
                        Toast.LENGTH_SHORT).show();



                // 여기에 태그 기능 구현

                // 태그가 완료되면 이전 페이지로 카드값 보내기
                // 태그가 완료 되지 않으면 다시 태그하라고 토스트 메시지 보내기

            }
        });
    }
}