package com.example.mymvp.bean;

/**
 * Created by Administrator on 2017/1/18.
 */

public class VersionBean {

    private String version;
    private String note;
    private String date;
    private String url;
    private String forceUpdate;

    @Override
    public String toString() {
        return "{" +
                "version='" + version + '\'' +
                ", note='" + note + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", forceUpdate='" + forceUpdate + '\'' + "}";
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
