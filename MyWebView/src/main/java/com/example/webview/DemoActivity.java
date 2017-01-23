package com.example.webview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/1/12.
 */

public class DemoActivity extends Activity {
    private FanshapedView fanshapedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        fanshapedView = (FanshapedView) findViewById(R.id.myImageView);
        SharedPreferences.Editor sp = getSharedPreferences("records", MODE_PRIVATE).edit();
        sp.putInt("test1", 1);
        sp.putInt("test2", 2);
        sp.putInt("test3", 4);
        sp.putInt("test4", 8);
        sp.putInt("sum", 1 + 2 + 4 + 8);
        sp.commit();
        //刷新
        fanshapedView.postInvalidate();
    }
}
