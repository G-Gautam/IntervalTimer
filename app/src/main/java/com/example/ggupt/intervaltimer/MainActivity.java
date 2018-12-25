package com.example.ggupt.intervaltimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText duration;
    EditText repetition;
    EditText interval;
    TextView statusTimer;
    Button setTimer;
    CountDownTimer timer;

    int durationInt, repetitionInt;
    long intervalInt;
    boolean isTimerRunning = false;
    int timesPlayed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTimer = (TextView) findViewById(R.id.textView5);
        duration = (EditText) findViewById(R.id.editText3);
        interval = (EditText) findViewById(R.id.editText4);
        repetition = (EditText) findViewById(R.id.editText5);
        setTimer = (Button) findViewById(R.id.button);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.beep_01a);

        setTimer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (!isTimerRunning) {
                    setTimer.setText("Cancel Timer");
                    durationInt = Integer.parseInt(duration.getText().toString());
                    intervalInt = Integer.parseInt(interval.getText().toString());
                    repetitionInt = Integer.parseInt(repetition.getText().toString());

                    makeTimer(intervalInt, repetitionInt, durationInt, mediaPlayer);

                    isTimerRunning = false;
                }
                else if(isTimerRunning){
                    setTimer.setText("Set Alarm");
                    timer.cancel();
                    statusTimer.setText("Timer Status");
                    isTimerRunning = false;
                }
            }
        });
    }

    public void makeTimer(final long interval, final int r, final int d, final MediaPlayer m){
        timesPlayed = 1;
        isTimerRunning = true;

        final int rNew = r -1;
        timer = new CountDownTimer(interval * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                statusTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                int timesPlayed = 1;
                statusTimer.setText("done!");
                m.start();
                if (rNew != 0) {
                    makeTimer(interval, rNew, d, m);
                }
            };
        }.start();

        m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (timesPlayed < d) {
                    timesPlayed++;
                    m.start();
                }
            }
        });

    }

}
