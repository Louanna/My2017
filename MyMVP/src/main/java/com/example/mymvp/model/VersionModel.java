package com.example.mymvp.model;

import android.util.Log;

import com.example.mymvp.base.BaseModel;
import com.example.mymvp.bean.VersionBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/18.
 */

public class VersionModel extends BaseModel {

    public void checkVersion() {

        //filter\flatMap实例
        BaseModel.httpService.checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<VersionBean, Boolean>() {
                    @Override
                    public Boolean call(VersionBean aVoid) {
                        //filter过滤：判断是否登录过，如果false就会跳过下面的操作
                        //注意:返回false时,Http请求仍然会被发起,只是subscribe不会被响应
                        Log.d("call", "已经登录过了");
                        return true;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<VersionBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(VersionBean bean) {
                        //将原本的Http请求返回类型VersionBean转换为String
                        Log.d("call", "bean=" + bean.toString());
                        String str = "123";
                        return Observable.just(str);
                    }
//                    @Override
//                    public Observable<String> call(List<String> urls) {
//                        return Observable.from(urls);
//                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onError", "e=" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("onNext", "s=" + s.toString());
                    }
                });

        //zipWith实例
        //注意zipWith和subscribeOn\observeOn的前后顺序
        BaseModel.httpService.checkVersion2()
                .zipWith(BaseModel.httpService.checkVersion(), new Func2<VersionBean, VersionBean, VersionBean>() {

                    @Override
                    public VersionBean call(VersionBean versionBean2, VersionBean versionBean) {
                        //versionBean即checkVersion()的返回结果
                        //versionBean2即checkVersion2()的返回结果
                        Log.d("LoginModel", "versionBean=" + versionBean.toString());
                        Log.d("LoginModel", "versionBean2=" + versionBean2.toString());
                        return versionBean;
                    }
                })
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
    }
}
