package com.example.gs.mvpdemo.factory;

import com.example.gs.mvpdemo.converter.FileResponseConverter;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/1/19.
 */

public class FileConverterFactory extends Converter.Factory {

    private String filePath,fileName;

    public static FileConverterFactory create(String filePath, String fileName) {
        return new FileConverterFactory(filePath, fileName);
    }

    private FileConverterFactory(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public Converter<ResponseBody, File> responseBodyConverter(Type type, Annotation[] annotations,
                                                               Retrofit retrofit) {
        return new FileResponseConverter(filePath,fileName);
    }
}
