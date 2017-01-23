package com.example.gs.mvpdemo.converter;

import com.example.gs.mvpdemo.base.BaseHttpResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ListResponseConverter implements Converter<ResponseBody, List> {
    @Override
    public List convert(ResponseBody value) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BaseHttpResult>>() {
        }.getType();

        return gson.fromJson(value.string(), type);
    }
}
