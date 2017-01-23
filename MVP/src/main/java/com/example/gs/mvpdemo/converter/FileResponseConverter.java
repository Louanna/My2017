package com.example.gs.mvpdemo.converter;

import com.example.gs.mvpdemo.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2017/1/19.
 */

public class FileResponseConverter implements Converter<ResponseBody, File> {

    private String filePath,fileName;

    public FileResponseConverter(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public File convert(ResponseBody value) throws IOException {
        return FileUtils.writeFile(filePath,fileName,value.byteStream());
    }
}
