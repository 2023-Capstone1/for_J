package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HabitCheckNFC extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    boolean readTagEnabled = true;
    String habit_nfc = null;
    String loginID;
    private LinearLayout before_tag;
    private LinearLayout after_tag;
    private TextView habit_message;
    private LocalDate selectedDate;
    private DateTimeFormatter formatter; // 달력 날짜 포맷
    private String selectedDateStr;

    // 0.5초 후에 로그인 화면으로 넘어감
    private static final int SPLASH_TIME = 500;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_check);

        selectedDate = LocalDate.now();

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        selectedDateStr = selectedDate.format(formatter);

        IdSave idSave = (IdSave) getApplication();
        loginID = idSave.getUserId();

        before_tag = findViewById(R.id.before_tag);
        after_tag = findViewById(R.id.after_tag);
        habit_message = findViewById(R.id.habit_message);

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC는 이 장치에서 지원되지 않습니다.", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "태그 준비가 완료되었어요. NFC를 태그해주세요!", Toast.LENGTH_SHORT).show();
            readTagEnabled = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
//            nfcAdapter.disableForegroundDispatch(this);
            nfcAdapter.enableForegroundDispatch(HabitCheckNFC.this, pendingIntent, null, null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//        String nfcValue = tag.getId().toString();
//        Toast.makeText(this, "nfcValue=" + nfcValue, Toast.LENGTH_SHORT).show();
        if (intent.getComponent().getClassName().equals(HabitCheckNFC.class.getName())) {
            if (readTagEnabled) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//                System.out.println("readTagEnaled 은 트루임");
                if (tag != null) {
//                    System.out.println("태그 널 아님");
                    readTag(tag);
                }
            }
        }

    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    private void readTag(Tag tag){
        if (tag != null) {
            byte[] tagId = tag.getId();
            String serialNumber = bytesToHexString(tagId);
            habit_nfc = serialNumber;

            String updateStateURL = "http://203.250.133.162:8080/habitAPI/update_habit_state/" + loginID + "/" + selectedDateStr + "/" + habit_nfc;
            ApiService updateStateAPI = new ApiService();
            updateStateAPI.getUrl(updateStateURL);
            if (updateStateAPI.getStatus() == 200){
                before_tag.setVisibility(View.GONE);
                after_tag.setVisibility(View.VISIBLE);

                habit_message.setText(updateStateAPI.getValue("name") + "해빗이 완료되었습니다.");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, SPLASH_TIME);
            }else{
                Toast.makeText(this, "등록되어 있지 않은 nfc 토큰 입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
