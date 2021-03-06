package com.tt.voicecurve;

import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private RecordThread recordThread;
    private CurveView mCurveView;
    private boolean mIsStop = true;
    private Button mButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RecordThread.UPDATE_VIEW:
                    mCurveView.addVisiblePoint(new Point(msg.arg2, msg.arg1));
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) this.findViewById(R.id.button);
        mCurveView = (CurveView) this.findViewById(R.id.curve);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mIsStop) {
                    recordThread = new RecordThread(mHandler);
                    recordThread.startRecord();
                    mCurveView.clearScreen();
                    mButton.setText("stop");
                    mIsStop = false;
                } else {
                    recordThread.stopRecord();
                    mButton.setText("start");
                    mIsStop = true;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
