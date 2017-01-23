package com.example.mymvp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.gs.mvpdemo.ProApplication;
import com.example.gs.mvpdemo.http.Http;
import com.example.gs.mvpdemo.utils.ZipUtils;
import com.example.mymvp.base.BaseModel;
import com.example.gs.mvpdemo.subscriber.CommonSubscriber;
import com.example.gs.mvpdemo.transformer.CommonTransformer;
import com.example.gs.mvpdemo.utils.FileUtils;
import com.example.gs.mvpdemo.utils.SpUtils;
import com.example.mymvp.bean.FileBean;
import com.example.mymvp.bean.FilesBean;
import com.example.mymvp.bean.VersionBean;
import com.example.mymvp.manager.HttpManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by GaoSheng on 2016/11/26.
 * 20:53
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.model
 * 主要做一些数据处理呀,网路请求呀
 */

public class LoginModel extends BaseModel {
    private boolean isLogin = false;

    public boolean login(@NonNull String username, @NonNull String pwd, @NonNull final InfoHint
            infoHint) {

        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

//        httpService.login(username, pwd)
//                .compose(new CommonTransformer<LoginBean>())
//                .filter(new Func1<LoginBean, Boolean>() {
//                    @Override
//                    public Boolean call(LoginBean aVoid) {
//                        //filter过滤：判断是否登录过，如果false就会跳过下面的操作
//                        return isLogin;
//                    }
//                })
//                .filter(new Func1<LoginBean, Boolean>() {
//                    @Override
//                    public Boolean call(LoginBean aVoid) {
//                        //过滤：判断上次登录是否超过时间差
////                        Long lastTime= (Long) SPUtils.get(getApplicationContext(),Constant.LOGINTIME,0L);
////                        return System.currentTimeMillis()-lastTime >mTimeDifference;
//                        return true;
//                    }
//                })
//                .subscribe(new CommonSubscriber<LoginBean>(ProApplication.getmContext()) {
//                    @Override
//                    public void onNext(LoginBean loginBean) {
//                        isLogin = true;
//                        infoHint.successInfo(loginBean.getToken());
//                    }
//
//                    @Override
//                    protected void onError(ApiException e) {
//                        super.onError(e);
//                        isLogin = false;
//                        infoHint.failInfo(e.message);
//                    }
//                });

        SpUtils.put("token","7245ab53dc6c422a89002f1f6551ffb1");

//        File file = new File(FileUtils.SD_CARD + File.separator + "abced" +File.separator+"1617.png");
//        //创建RequwstBody对象
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        //使用RxJava方式调度任务并监听
//        BaseModel.httpService.uploadFile(requestBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new CommonSubscriber<List<FileBean>>(ProApplication.getmContext()) {
//
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("onError", "e="+e.toString());
//                    }
//
//                    @Override
//                    public void onNext(List<FileBean> fileBean) {
//                        Log.d("onNext", "fileBean="+fileBean.size());
//                    }
//                });

//        String zifFileURL = "http://download.eop.movitech.cn/eoopapp/android/v6.0.0/movit/eoop.zip";
////        String zifFileURL = "http://api.eop.movitech.cn/download/1484845200004/fullOrgList.zip";
//        String picFileURL = "https://p.bre600708.com/cmsContent/20161208/a4ca78a0-b416-4b78-9c2e-34a74ec465ba.jpg";
//
//        String fileName = zifFileURL.substring(zifFileURL.lastIndexOf("/")+1);
//        String filePath = "abced";
//
//        HttpManager.getFileService(filePath,fileName).downloadFile(zifFileURL)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Subscriber<File>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("onError", "e="+e.toString());
//                    }
//
//                    @Override
//                    public void onNext(File file) {
//                        Log.d("onNext", "file="+(null!=file?file.getName():null));
//                        try {
//                            ZipUtils.upZipFile(file, FileUtils.SD_CARD );//+ File.separator + "abced"
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("onNext", "upZipFile done");
//                    }
//                });

//        fileService
//                .download("https://p.bre600708.com/cmsContent/20161215/137a255b-9f44-4e1a-9a4a-344d38abb0f5.jpg")
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map(new Func1<ResponseBody, InputStream>() {
//                    @Override
//                    public InputStream call(ResponseBody responseBody) {
//                        return responseBody.byteStream();
//                    }
//                })
//                .observeOn(Schedulers.computation())
//                .doOnNext(new Action1<InputStream>() {
//                    @Override
//                    public void call(InputStream inputStream) {
//
//                        FileUtils.writeFile("abced","a01.jpeg",inputStream);
//
//                        Log.d("doOnNext", "called");
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<InputStream>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("onError", "e="+e.toString());
//                    }
//
//                    @Override
//                    public void onNext(InputStream inputStream) {
//                        Log.d("onNext", "inputStream="+inputStream);
//                    }
//                });

        BaseModel.httpService.checkVersion2()
//                .zipWith(BaseModel.httpService.checkVersion(), new Func2<VersionBean, VersionBean, VersionBean>() {
//
//                    @Override
//                    public VersionBean call(VersionBean versionBean2, VersionBean versionBean) {
//                        //versionBean即checkVersion()的返回结果
//                        //versionBean2即checkVersion2()的返回结果
//                        Log.d("LoginModel", "versionBean=" + versionBean.toString());
//                        Log.d("LoginModel", "versionBean2=" + versionBean2.toString());
//                        return versionBean;
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionBean>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onError", "e=" + e.toString());
                    }

                    @Override
                    public void onNext(VersionBean s) {
                        Log.d("onNext", "s=" + s.toString());
                    }
                });

        return isLogin;
    }

    //通过接口产生信息回调
    public interface InfoHint {
        void successInfo(String str);

        void failInfo(String str);
    }

}
