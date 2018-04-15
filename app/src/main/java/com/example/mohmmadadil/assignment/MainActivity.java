package com.example.mohmmadadil.assignment;

import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    TextView wifiStatusText;
    Switch wifiSwitch;

    TextToSpeech tts;
    EditText et;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = findViewById(R.id.button);
        et = findViewById(R.id.editText);

        //Wifi

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiStatusText = findViewById(R.id.text1);
        wifiSwitch = findViewById(R.id.switch1);

        if(wifiManager.isWifiEnabled()){
            wifiStatusText.setText("wifi is currently ON");
            wifiSwitch.setChecked(true);
        }else{
            wifiStatusText.setText("Wifi is currently OFF");
            wifiSwitch.setChecked(false);
        }

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    wifiManager.setWifiEnabled(true);
                    wifiStatusText.setText("wifi is currently ON");
                    Toast.makeText(MainActivity.this, "wifi is going to be on", Toast.LENGTH_SHORT).show();
                }else {
                    wifiManager.setWifiEnabled(false);
                    wifiStatusText.setText("Wifi is currently OFF");
                    Toast.makeText(MainActivity.this, "wifi is going to be off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.US);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = et.getText().toString();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    public void onPause(){
        if (tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}
