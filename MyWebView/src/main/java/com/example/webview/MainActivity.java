package com.example.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_CODE_PICK_IMAGE = 23;
    public final static int REQUEST_CODE_IMAGE_CAPTURE = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
//            if (mUploadMsg != null) {
//                mUploadMsg.onReceiveValue(null);
//            }
//
//            if (mUploadMsgForAndroid5 != null) {         // for android 5.0+
//                mUploadMsgForAndroid5.onReceiveValue(null);
//            }
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
            case REQUEST_CODE_PICK_IMAGE: {
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                        if (mUploadMsg == null) {
//                            return;
//                        }
//
//                        String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);
//
//                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
//                            Log.e(TAG, "sourcePath empty or not exists.");
//                            break;
//                        }
//                        Uri uri = Uri.fromFile(new File(sourcePath));
//                        mUploadMsg.onReceiveValue(uri);

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        if (mUploadMsgForAndroid5 == null) {        // for android 5.0+
//                            return;
//                        }
//
//                        String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);
//
//                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
//                            Log.e(TAG, "sourcePath empty or not exists.");
//                            break;
//                        }
//                        Uri uri = Uri.fromFile(new File(sourcePath));
//                        mUploadMsgForAndroid5.onReceiveValue(new Uri[]{uri});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
