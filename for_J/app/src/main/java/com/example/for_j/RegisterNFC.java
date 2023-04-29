package com.example.for_j;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.Charset;

public class RegisterNFC extends AppCompatActivity {

    Button RNFC_TagBtn;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] intentFiltersArray;
    boolean readTagEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nfc);

        RNFC_TagBtn = (Button) findViewById(R.id.RNFC_TagBtn);

        RNFC_TagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "태그 준비가 완료되었어요. NFC를 태그해주세요!", Toast.LENGTH_SHORT).show();
                readTagEnabled = true; // Set the flag when the button is clicked
            }
        });

        // Set up NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC는 이 장치에서 지원되지 않습니다.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_IMMUTABLE);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Unable to add data type.", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef};
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Check if the flag is true before reading the tag
        if (readTagEnabled) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                readTag(tag);
            }
        }
    }

    private void readTag(Tag tag) {
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            Toast.makeText(this, "태그가 NDEF 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            if (ndefMessage != null) {
                String message = "";
                for (NdefRecord record : ndefMessage.getRecords()) {
                    byte[] payload = record.getPayload();
                    message += new String(payload, Charset.forName("UTF-8"));
                }
                Toast.makeText(this, "NFC 태그값: " + message, Toast.LENGTH_SHORT).show();
                System.out.println("NFC 태그값: " + message);
            } else {
                Toast.makeText(this, "태그의 NDEF 메시지를 읽을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | FormatException e) {
            Toast.makeText(this, "태그를 읽는 동안 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                ndef.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read the NFC tag's data and perform the desired operation here
        // For example, you can send the card value to the previous page
        // or display a toast message to retag if the tagging isn't complete
    }
}
