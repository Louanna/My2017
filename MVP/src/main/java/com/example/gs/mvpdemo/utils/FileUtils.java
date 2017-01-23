package com.example.gs.mvpdemo.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2017/1/19.
 */

public class FileUtils {

    private static int FILESIZE = 500 * 1024;//500k


    public static final String SD_CARD = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param fileName
     * @param inputStream
     * @return
     */
    public static File writeFile(String filePath, String fileName, InputStream inputStream) {
        File file = null;
        OutputStream output = null;
        try {

            Log.d("writeFile", "SD_CARD="+SD_CARD);

            File fileDir = new File(SD_CARD + "/" + filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            file = new File(SD_CARD + "/" + filePath + "/" + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[FILESIZE];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public File getFileFromSDByFileLink(String SDpath, String fileLink) {
        String fileName = this.geFileName(fileLink);
        fileName = URLDecoder.decode(URLDecoder.decode(fileName));
        File file = new File(SDpath + fileName);
        return file;
    }

    public File write2SDFromInput(String SDpath, String fileName,
                                  InputStream input, UpdataBarListerner ubl) {
        File file = null;
        BufferedInputStream bis = new BufferedInputStream(input);
        BufferedOutputStream bof = null;
        int progress = 0;
        int flag = 0;
        try {
            File dir = new File(SDpath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(SDpath + fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            bof = new BufferedOutputStream(new FileOutputStream(file));
            int length;
            byte buffer[] = new byte[1024];
            while (-1 != (length = bis.read(buffer))) {
                bof.write(buffer, 0, length);
                progress += length;
                ++flag;
                if (flag % 10 == 0) {
                    bof.flush();
                }
                ubl.onUpdate(progress, 0);
            }
            bof.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bof) {
                    bof.close();
                    bof = null;
                }
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public boolean existSoftwareForTheFile(Context activity, String fileLink) {

        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        String mimetype = getMIMEType(fileLink);
        mIntent.setDataAndType(Uri.fromParts("file", "", null), mimetype);
        ResolveInfo resolveInfo = activity.getPackageManager().resolveActivity(
                mIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (null == resolveInfo) {
            return false;
        }
        return true;
    }

    public String getFileSuffix(File file) {
        String fName = file.getName();
        /* 取得扩展名 */
        String fileSuffix = fName.substring(fName.lastIndexOf("."),
                fName.length()).toLowerCase();
        return fileSuffix;
    }

    /**
     * 得到文件类型头
     */
    public String getMIMEType(String fileLink) {
        String type = null;
        try {
            File file = new File(fileLink);

            String fileSuffix = getFileSuffix(file);

            type = "*/*";
            if (fileSuffix == "") {
                return type;
            }
            // 在MIME和文件类型的匹配表中找到对应的MIME类型。
            for (int i = 0; i < MIME_MapTable.length; i++) {
                if (fileSuffix.equals(MIME_MapTable[i][0])) {
                    type = MIME_MapTable[i][1];
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    public static String geFileName(String fileLink) {
        try {
            File file = new File(fileLink);
            String fileName = file.getName();
            int separatorIndex = fileLink.lastIndexOf("fileName=");
            if (separatorIndex == -1) {
                fileName = fileLink.substring(fileLink.lastIndexOf("/") + 1);
            }
            return (separatorIndex < 0) ? fileName : fileLink.substring(
                    separatorIndex + 9, fileLink.length()).replaceAll("/", "_");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    // 建立一个MIME类型与文件后缀名的匹配表
    private final String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".aspx", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".prop", "text/plain"},
            {".rar", "application/x-rar-compressed"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/xml"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/zip"},
            {"", "*/*"},
            {".xls", "application/vnd.ms-excel"}};

    /**
     * 打开文件
     *
     * @param file
     */
    public void openFile(Context context, File file) {
        try {

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型
            String type = getMIMEType(file);
            // 设置intent的data和Type属性。
            intent.setDataAndType(Uri.fromFile(file), type);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "打开文件出错，请检查是否安装相应软件", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();

        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

		/* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;

        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 判断存储卡上的剩余空间是否大于sizeMb，单位为M
     *
     * @param sizeMb
     * @return
     */
    public boolean isAvaiableSpace(int sizeMb) {
        boolean ishasSpace = false;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            if (availableSpare > sizeMb) {
                ishasSpace = true;
            }
        }
        return ishasSpace;
    }

    public String getFileName(String path) {
        int index = path.lastIndexOf("/");
        String basePath = path.substring(index + 1).replace("?", "");
        if (basePath.endsWith(".png") || basePath.endsWith(".jpg")
                || basePath.endsWith(".amr") || basePath.endsWith(".mp3")) {
            return basePath;
        } else {
            return basePath + ".png";
        }
    }

    public static boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024 * 4];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            } else {
                isok = false;
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            isok = false;
        }
        return isok;
    }

    // 判断机器Android是否已经root，即是否获取root权限
    protected boolean haveRoot() {

        int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        if (i != -1)
            return true;
        return false;
    }

    protected int execRootCmdSilent(String paramString) {
        Object localObject = -1;
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            localObject = localProcess.exitValue();
            return (Integer) localObject;
        } catch (Exception localException) {
            return (Integer) localObject;
        }
    }


    /**
     * 专为Android4.4以后设计的从Uri获取文件绝对路径，以前的方法已不好使
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public interface UpdataBarListerner {

        public void onUpdate(int value, int status);

        public void onError(int value, int status);
    }


    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_USER
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {

                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
