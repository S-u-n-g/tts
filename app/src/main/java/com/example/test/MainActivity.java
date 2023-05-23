package com.example.test;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;              // TTS 변수 선언
    private EditText editText;
    private Button button01;

    private SeekBar seekBar;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText) findViewById(R.id.editText);
        button01 = (Button) findViewById(R.id.button01);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


        // TTS를 생성하고 OnInitListener로 초기화 한다.
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editText에 있는 문장을 읽는다.
                tts.speak(editText.getText().toString(),TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // SeekBar 값이 변경될 때마다 호출됩니다.
                float speed = (float) seekBar.getProgress() / 100;
                tts.setSpeechRate(speed);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 사용자가 SeekBar를 터치하기 시작할 때 호출됩니다.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 사용자가 SeekBar를 터치를 끝낼 때 호출됩니다.
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
