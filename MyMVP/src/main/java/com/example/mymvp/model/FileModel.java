package com.example.mymvp.model;

import android.util.Log;

import com.example.gs.mvpdemo.ProApplication;
import com.example.mymvp.base.BaseModel;
import com.example.gs.mvpdemo.subscriber.CommonSubscriber;
import com.example.gs.mvpdemo.transformer.CommonTransformer;
import com.example.mymvp.bean.FileBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/1/18.
 */

public class FileModel extends BaseModel {

    public void uploadFiles(File file) {

        //创建RequwstBody对象
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        //使用RxJava方式调度任务并监听
//        BaseModel.httpService.uploadFile(requestBody)
//                .compose(new CommonTransformer<FileBean>())
//                .subscribe(new CommonSubscriber<FileBean>(ProApplication.getmContext()) {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(FileBean fileBean) {
//
//                    }
//                });
    }

    public void uploadFiles(File... files) {
        //组装partMap对象
//        Map<String, RequestBody> partMap = new HashMap<>();
//        for (File file : files) {
//            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
//            partMap.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
//        }
//        //使用RxJava方式调度任务并监听
//        BaseModel.httpService.uploadFiles(partMap)
//                .compose(new CommonTransformer<List<String>>())
//                .subscribe(new CommonSubscriber<List<String>>(ProApplication.getmContext()) {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("test", "upload error" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<String> fileUrl) {
//                        Log.d("test", "upload Success");
//                    }
//                });
    }

}
