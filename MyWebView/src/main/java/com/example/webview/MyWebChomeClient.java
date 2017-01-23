package com.example.webview;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/1/10.
 * <p>
 * 在Android4.4之前，Webview的webkit中支持openFileChooser.
 * 当Webview加载一个HTML页面，点击按钮需要模拟form提交的方式去上传文件时，
 * 就会回调：openFileChooser(...)
 * <p>
 * Android5.0+，webkit不再支持penFileChooser(...)
 * 提供了一个代替的方法:onShowFileChooser
 */
public class MyWebChomeClient extends WebChromeClient {

    //回调接口
    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public MyWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    //Android4.4之前
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    //Android5.0+
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        return mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(webView, filePathCallback, fileChooserParams);
    }

    public interface OpenFileChooserCallBack {
        // for API - Version below 5.0.
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        // for API - Version above 5.0 (contais 5.0).
        boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                                FileChooserParams fileChooserParams);
    }
}
