package com.example.ggupt.intervaltimer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTimer = (TextView) findViewById(R.id.textView5);
        duration = (EditText) findViewById(R.id.editText3);
        interval = (EditText) findViewById(R.id.editText4);
        repetition = (EditText) findViewById(R.id.editText5);
        setTimer = (Button) findViewById(R.id.button);

        setTimer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (!isTimerRunning) {
                    setTimer.setText("Cancel Timer");
                    durationInt = Integer.parseInt(duration.getText().toString());
                    intervalInt = Integer.parseInt(interval.getText().toString());
                    repetitionInt = Integer.parseInt(repetition.getText().toString());

                    timer = new CountDownTimer(intervalInt * 1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            statusTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            statusTimer.setText("done!");
                            repetitionInt--;
                            if(repetitionInt > 0){
                                timer = new CountDownTimer(intervalInt * 1000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        statusTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                                    }

                                    public void onFinish() {
                                        statusTimer.setText("done!");
                                        repetitionInt--;
                                        if(repetitionInt > 0){

                                        }
                                    }
                                }.start();
                            }
                        }
                    }.start();

                    isTimerRunning = true;
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
}
