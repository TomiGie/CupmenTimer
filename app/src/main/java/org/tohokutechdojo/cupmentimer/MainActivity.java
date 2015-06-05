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
    private final static int CUPMEN_COMPLETE_TIME       = 10;  // カップ麺完成時間(秒)
    private final static int CUPMEN_BAD_TIME            = 5;  // カップ麺のびる時間(秒)

    private int mCupmenImageDefault  = R.mipmap.men_normal;
    private int mCupmenImageWaiting  = R.mipmap.men_waiting;
    private int mCupmenImageComplete = R.mipmap.men_complete;
    private int mCupmenImageBad      = R.mipmap.men_bad;

    private TextView mTimeTextView;
    private CupmenTimer mCupmenTimer;
    private ImageView mCupmenImage;
    private boolean mTimerFlag;

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
                    initializeCupmenImage();
                }
                break;
        }
    }

    public void setLayouts() {
        mTimeTextView = (TextView) findViewById(R.id.time_textView);
        mCupmenImage = (ImageView) findViewById(R.id.image_cupmen);

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
        mTimerFlag = true;
        mCupmenTimer = new CupmenTimer(CUPMEN_COUNT_DOWN_TIME * 1000, CUPMEN_COUNT_DOWN_INTERVAL);
    }

    /**
     * カップ麺の画像を初期状態に戻す
     */
    public void initializeCupmenImage() {
        mCupmenImage.setImageResource(mCupmenImageDefault);
    }

    /**
     * カップ麺の状態をチェックして画像を変更する
     * CupmenTimerの onTick() で1秒毎に呼ばれる
     */
    public void checkCupmenState(int checkTime) {
        if (checkTime <= CUPMEN_BAD_TIME) {
            mCupmenImage.setImageResource(mCupmenImageBad);
        }
        else if (checkTime <= CUPMEN_COMPLETE_TIME) {
            mCupmenImage.setImageResource(mCupmenImageComplete);
        }
        else {
            mCupmenImage.setImageResource(mCupmenImageWaiting);
        }
    }

    /**
     * カウントダウンタイマークラス
     */
    public class CupmenTimer extends CountDownTimer {

        private int mSecond = CUPMEN_COUNT_DOWN_TIME;

        public CupmenTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            setTimeText();
        }

        @Override
        public void onTick(long l) {
            //CUPMEN_COUNT_DOWN_INTERVAL秒おきに呼び出されるメソッド
            mSecond--;
            setTimeText();
            checkCupmenState(mSecond);
        }

        @Override
        public void onFinish() {
            //タイマー終了時に呼び出されるメソッド
            mSecond = CUPMEN_COUNT_DOWN_TIME;
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
