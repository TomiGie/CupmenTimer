package org.tohokutechdojo.cupmentimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTimeTextView;
    private CupmenTimer mCupmenTimer;
    private boolean mTimerFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerFlag = true;
        mTimeTextView = (TextView) findViewById(R.id.time_textView);

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        Button stopButton = (Button) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

        mCupmenTimer = new CupmenTimer(180000, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                if (mTimerFlag){
                    mCupmenTimer.start();
                    mTimerFlag = false;
                }
                break;

            case R.id.stop_button:
                if (!mTimerFlag){
                    mCupmenTimer.onFinish();
                    mCupmenTimer.cancel();
                    mTimerFlag = true;
                }
                break;
        }
    }

    public class CupmenTimer extends CountDownTimer {

        private int mSecond = 180;

        public CupmenTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            setTimeText();
        }

        @Override
        public void onTick(long l) {
            //1秒おきに呼び出されるメソッド
            mSecond--;
            setTimeText();
        }

        @Override
        public void onFinish() {
            //タイマー終了時に呼び出されるメソッド
            mSecond = 180;
            setTimeText();
        }

        private void setTimeText() {
            int minute = getMinute(mSecond);
            int second = getSecond(minute, mSecond);
            String time = getTimeText(minute, second);
            mTimeTextView.setText(time);
        }

        private int getMinute(int second) {
            int minute = second / 60;
            return minute;
        }

        private int getSecond(int minute, int beforeSecond) {
            if (beforeSecond < 60) {
                return beforeSecond;
            } else {
                return beforeSecond - (minute * 60);
            }
        }

        private String getTimeText(int minute, int second) {
            //分の桁を作成
            String tmpMinute;
            if (minute < 10) {
                tmpMinute = "0" + String.valueOf(minute);
            } else {
                tmpMinute = String.valueOf(minute);
            }

            //秒の桁を作成
            String tmpSecond;
            if (second < 10) {
                tmpSecond = "0" + String.valueOf(second);
            } else {
                tmpSecond = String.valueOf(second);
            }
            return tmpMinute + ":" + tmpSecond;
        }
    }
}
