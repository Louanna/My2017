package com.example.gs.mvpdemo.http;

import com.example.gs.mvpdemo.ProApplication;
import com.example.gs.mvpdemo.factory.FileConverterFactory;
import com.example.gs.mvpdemo.factory.ListConverterFactory;
import com.example.gs.mvpdemo.url.UrlHelper;
import com.example.gs.mvpdemo.utils.NetworkUtil;
import com.example.gs.mvpdemo.utils.SpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GaoSheng on 2016/9/14.
 */
public class Http {

    private static Retrofit retrofit;

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("token", (String) SpUtils.get("token", ""))
//                        .addQueryParameter("phoneSystem", "")
//                        .addQueryParameter("phoneModel", "")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("token", (String) SpUtils.get("token", ""))
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkAvailable(ProApplication.getmContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkAvailable(ProApplication.getmContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    private static OkHttpClient createOkHttpClient() {
        //添加一个log拦截器,打印所有的log
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置 请求的缓存的大小跟位置
        File cacheFile = new File(ProApplication.getmContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小

        return new OkHttpClient
                .Builder()
                .addInterceptor(addQueryParameterInterceptor())  //参数添加
                .addInterceptor(addHeaderInterceptor()) // token过滤
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .cache(cache)  //添加缓存
                .connectTimeout(60l, TimeUnit.SECONDS)
                .readTimeout(60l, TimeUnit.SECONDS)
                .writeTimeout(60l, TimeUnit.SECONDS)
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Http.class) {
                if (retrofit == null) {
                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(UrlHelper.BASE_URL)  //自己配置
                            .client(createOkHttpClient())
                            //TODO 奇怪的,按照API添加多个Factory,应该按照添加顺序检查适配性,但是测试下来,没有按照顺序检查,第一个检查不通过就会报错!
                            .addConverterFactory(ListConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create()) //这里是用的fastjson的
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static Retrofit getFileRetrofit(String filePath, String fileName) {
        synchronized (Http.class) {
            // 获取retrofit的实例
            return new Retrofit
                    .Builder()
                    .baseUrl(UrlHelper.BASE_URL)  //自己配置
                    .client(createOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(FileConverterFactory.create(filePath, fileName))
                    .build();
        }
    }

}
