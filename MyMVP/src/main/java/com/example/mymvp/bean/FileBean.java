package com.example.mymvp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/18.
 */

public class FileBean {

    @SerializedName("id")
    private String id;
    @SerializedName("oName")
    private String oName;
    @SerializedName("suffix")
    private String suffix;
    @SerializedName("uName")
    private String uName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
