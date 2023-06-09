package com.example.for_j;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HabitRegisterNFC extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private Button RNFC_TagBtn;
    private PendingIntent pendingIntent;
    boolean readTagEnabled = false;
    String habit_nfc = null;
    String loginID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nfc);

        IdSave idSave = (IdSave) getApplication();
        loginID = idSave.getUserId();

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC는 이 장치에서 지원되지 않습니다.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        RNFC_TagBtn = (Button) findViewById(R.id.RNFC_TagBtn);

        RNFC_TagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nfcAdapter != null) {
                    Toast.makeText(getApplicationContext(), "태그 준비가 완료되었어요. NFC를 태그해주세요!", Toast.LENGTH_SHORT).show();
                    readTagEnabled = true;
                    nfcAdapter.enableForegroundDispatch(HabitRegisterNFC.this, pendingIntent, null, null);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
            //nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
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
        if (intent.getComponent().getClassName().equals(HabitRegisterNFC.class.getName())) {
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

            String checkNFCExistURL = "http://203.250.133.162:8080/checkAPI/get_is_nfc_exist/" + loginID + "/" + "habit" + "/" + habit_nfc;
            ApiService checkNFCExistAPI = new ApiService();
            checkNFCExistAPI.getUrl(checkNFCExistURL);

            if (Integer.parseInt(checkNFCExistAPI.getValue("is_nfc_exist")) == 1){
                Toast.makeText(this, "이미 등록된 nfc 입니다. 새로운 nfc를 등록해주세요", Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this, "시리얼 번호: " + serialNumber, Toast.LENGTH_SHORT).show();
                // Set readTagEnabled to false after the tag is read
                readTagEnabled = false;

                Intent data = new Intent();
                data.putExtra("habit_nfc", habit_nfc);
                setResult(RESULT_OK, data);

                finish();
            }
        }
    }

}
