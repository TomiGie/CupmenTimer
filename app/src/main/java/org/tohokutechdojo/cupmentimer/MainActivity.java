package org.tohokutechdojo.cupmentimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int CUPMEN_COUNT_DOWN_INTERVAL = 1000; // カウントダウンの間隔(ミリ秒)
    private final static int CUPMEN_COUNT_DOWN_TIME     = 15;  // カウントダウンの時間(秒)
    private final static int CUPMEN_COMPLETE_TIME       = 5;  // カップ麺完成時間(秒)
    private final static int CUPMEN_BAD_TIME            = 1;  // カップ麺のびる時間(秒)

    private TextView mTimeTextView;
    private CupmenTimer cupmenTimer;
    private ImageView cupmenImage;
    private boolean isTimerWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayouts();
        setCupmenTimer();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                if (!isTimerWorking){
                    cupmenTimer.start();
                    isTimerWorking = true;
                }
                break;

            case R.id.stop_button:
                if (isTimerWorking){
                    cupmenTimer.onFinish();
                    cupmenTimer.cancel();
                    isTimerWorking = false;
                    initializeCupmenImage();
                }
                break;
        }
    }

    /**
     * カップ麺の状態をチェックして画像を変更する
     * CupmenTimerの onTick() で1秒毎に呼ばれる
     */
    public void checkCupmenState(int checkTime) {
        // ラーメンが伸びる時間を過ぎたら
        if (checkTime <= CUPMEN_BAD_TIME) {
            cupmenImage.setImageResource(R.mipmap.men_waiting);
        }
        // ラーメン完成の時間が過ぎたら
        else if (checkTime <= CUPMEN_COMPLETE_TIME) {
            cupmenImage.setImageResource(R.mipmap.men_waiting);
        }
        // その他(ラーメン完成前)
        else {
            cupmenImage.setImageResource(R.mipmap.men_waiting);
        }
    }

    public void setLayouts() {
        mTimeTextView = (TextView) findViewById(R.id.time_textView);
        cupmenImage = (ImageView) findViewById(R.id.image_cupmen);

        ImageButton startButton = (ImageButton) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        ImageButton stopButton = (ImageButton) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        initializeCupmenImage();
    }

    /**
     * カウントダウンタイマーの秒数等をセット
     */
    public void setCupmenTimer() {
        isTimerWorking = false;
        cupmenTimer = new CupmenTimer(CUPMEN_COUNT_DOWN_TIME * 1000, CUPMEN_COUNT_DOWN_INTERVAL);
    }

    /**
     * カップ麺の画像を初期状態に戻す
     */
    public void initializeCupmenImage() {
        cupmenImage.setImageResource(R.mipmap.men_normal);
    }

    /**
     * カウントダウンタイマークラス
     */
    public class CupmenTimer extends CountDownTimer {

        private int countDownTime = CUPMEN_COUNT_DOWN_TIME;

        public CupmenTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            setTimeText();
        }

        @Override
        public void onTick(long l) {
            //CUPMEN_COUNT_DOWN_INTERVAL秒おきに呼び出されるメソッド
            countDownTime--;
            setTimeText();
            checkCupmenState(countDownTime);
        }

        @Override
        public void onFinish() {
            //タイマー終了時に呼び出されるメソッド
            countDownTime = CUPMEN_COUNT_DOWN_TIME;
            setTimeText();
        }

        private void setTimeText() {
            int minute = getMinute(countDownTime);
            int second = getSecond(minute, countDownTime);
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
