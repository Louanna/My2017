package scp.com.frescobase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);//初始化Fresco使用默认配置
        setContentView(R.layout.activity_main);
        initView();
    }

    //自定义Fresco磁盘缓存

//    public void initFresco(Context context, String diskCacheUniqueName){
//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
//                .setMaxCacheSize(DISK_CACHE_SIZE_HIGH)
//                .setMaxCacheSizeOnLowDiskSpace(DISK_CACHE_SIZE_LOW)
//                .setMaxCacheSizeOnVeryLowDiskSpace(DISK_CACHE_SIZE_VERY_LOW)
//                .build();
//
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//                .setMainDiskCacheConfig(diskCacheConfig)
//                .build();
//        Fresco.initialize(context, config);
//    }

    private void initView() {
        //创建SimpleDraweeView对象
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.main_sdv);
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse("http://my.csdn.net/uploads/avatar/4/E/8/1_y1scp.jpg");
        //开始下载
        simpleDraweeView.setImageURI(imageUri);

        //创建DraweeController
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //重试之后要加载的图片URI地址
                .setUri(imageUri)
                        //设置点击重试是否开启
                .setTapToRetryEnabled(true)
                        //设置旧的Controller
                .setOldController(simpleDraweeView.getController())
                        //构建
                .build();

        //设置DraweeController
        simpleDraweeView.setController(controller);
    }
}
