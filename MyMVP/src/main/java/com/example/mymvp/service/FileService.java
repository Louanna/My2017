package com.example.mymvp.service;

import java.io.File;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface FileService {

    //下载文件
    @GET
    Observable<File> downloadFile(@Url String url);
}
