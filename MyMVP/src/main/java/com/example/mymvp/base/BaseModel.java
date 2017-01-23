package com.example.mymvp.base;

import com.example.gs.mvpdemo.mvp.IModel;
import com.example.mymvp.manager.HttpManager;
import com.example.mymvp.service.HttpService;

/**
 * Created by gaosheng on 2016/12/1.
 * 23:13
 * com.example.gs.mvpdemo.base
 */

public class BaseModel implements IModel {

    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = HttpManager.getHttpService();
    }

}
