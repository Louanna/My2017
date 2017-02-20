package com.example.makeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvUrl;
    private TextView tvAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUrl = (TextView) findViewById(R.id.tvUrl);
        tvAd = (TextView) findViewById(R.id.tvAd);

        tvUrl.setText(Constant.URL);
        String adStr = Constant.HAS_AD ? "我是广告" : "没有广告 你看不见我 看不见我";
        tvAd.setText(adStr);
    }
}