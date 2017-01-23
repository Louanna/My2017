package com.example.mymvp.manager;

import com.example.gs.mvpdemo.http.Http;
import com.example.mymvp.service.FileService;
import com.example.mymvp.service.HttpService;

/**
 * Created by Administrator on 2017/1/22.
 */

public class HttpManager {

    private static HttpService httpService;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static FileService getFileService(String filePath, String fileName) {
        return Http.getFileRetrofit(filePath, fileName).create(FileService.class);
    }

    public static HttpService getHttpService() {
        if (httpService == null) {
            httpService = Http.getRetrofit().create(HttpService.class);
        }
        return httpService;
    }
}
