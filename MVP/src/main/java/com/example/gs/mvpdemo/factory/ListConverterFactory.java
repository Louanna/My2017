package com.example.gs.mvpdemo.factory;

import android.util.Log;

import com.example.gs.mvpdemo.converter.ListResponseConverter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ListConverterFactory extends Converter.Factory {
    public static ListConverterFactory create() {
        return new ListConverterFactory();
    }

    private ListConverterFactory() {

    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        if(type instanceof GenericArrayType){
            Log.d("test","GenericArrayType");

        }else  if(type instanceof ParameterizedType){
            Log.d("test","ParameterizedType");

        }else  if(type instanceof WildcardType){
            Log.d("test","WildcardType");

        }else  if(type instanceof TypeVariable){
            Log.d("test","TypeVariable");

        }else{
            Log.d("test","type =" +type.toString());
        }
        return new ListResponseConverter();
    }
}
