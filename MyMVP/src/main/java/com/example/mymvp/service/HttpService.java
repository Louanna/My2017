package com.example.mymvp.service;

import com.example.gs.mvpdemo.base.BaseHttpResult;
import com.example.mymvp.bean.FileBean;
import com.example.mymvp.bean.LoginBean;
import com.example.mymvp.bean.VersionBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by GaoSheng on 2016/9/13.
 * 网络请求的接口都在这里
 */

public interface HttpService {

    @FormUrlEncoded//拼参数到URL后面
    @POST
    //登录接口
    Observable<BaseHttpResult<LoginBean>> login(@Field("userName") String username, @Field
            ("passWord") String pwd);

    //检查版本
    @GET("v6.0.0/movit/ANDVersion.json")
    Observable<VersionBean> checkVersion();

    //检查版本
    @GET("v5.0.4/movit/ANDVersion.json")
    Observable<VersionBean> checkVersion2();

//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);

//    @GET("users/{userId}")//动态路径get请求 @Path
//    Call<List<VersionBean>> getUsers(@Path("userId") String userId);
//
//    @GET("users/{userId}")//拼接参数 @Query使用,Query 其实就是 Url 中 ‘?’ 后面的 key-value，比如：
//    Call<List<VersionBean>> getUsers(@Path("userId") String userId, @Query("age")int age);
//
//    @GET("users/{userId}")//拼接参数 @QueryMap使用
//    Call<List<VersionBean>> getUsers(@Path("userId") String userId, @QueryMap HashMap<String, String> paramsMap);

    //多文件上传
    @Multipart//表单提交
    @POST("upload")
    Observable<BaseHttpResult<List<String>>> uploadFiles(@PartMap Map<String, RequestBody> params);

    //单文件上传
    @Multipart
    @POST("upload")
    //此处@Part(“file\”; filename=\”avatar.png\”“)注释的含义是该RequestBody的名称为file，上传的文件名称为avatar.png。
    //@Path注解中的filename与上传文件的真实名称可以不匹配。
    Observable<List<FileBean>> uploadFile(@Part("file\"; filename=\"avatar.png\"") RequestBody file);

}
