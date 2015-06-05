package org.tohokutechdojo.cupmentimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeTextView = (TextView)findViewById(R.id.time_textView);

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        Button stopButton = (Button) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                break;
            case R.id.stop_button:
                break;
        }
    }

    public class CupmenTimer extends CountDownTimer{

        public CupmenTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {

        }
    }
}
