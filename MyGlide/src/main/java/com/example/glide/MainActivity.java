package com.example.glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

// Glide特点
// 使用简单,可配置度高,自适应程度高
// 支持常见图片格式  Jpg png gif webp
// 支持多种数据源   网络、本地、资源、Assets 等
// 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
// 生命周期集成    根据Activity/Fragment生命周期自动管理请求
// 高效处理Bitmap 使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.iv);

        //用来测试的图片地址
//        String imageUrl2 = "http://inews.gtimg.com/newsapp_bt/0/1010759732/641";
        //用来测试的图片地址
        String imageUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        Glide.with(this).load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.d("test", "e=" + e.toString());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);

        //简单的加载图片实例
        Glide.with(this).load(imageUrl).into(imageView);

        //设置加载中以及加载失败图片
        //api里面对placeholder()、error()函数中有多态实现 用的时候可以具体的熟悉一下
        Glide.with(this).load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);

        //设置跳过内存缓存
        Glide.with(this).load(imageUrl).skipMemoryCache(true).into(imageView);

        //设置下载优先级
        Glide.with(this).load(imageUrl).priority(Priority.NORMAL).into(imageView);

        //设置缓存策略
        Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

//        all:缓存源资源和转换后的资源
//        none:不作任何磁盘缓存
//        source:缓存源资源
//        result：缓存转换后的资源

        //设置加载动画
        //api也提供了几个常用的动画：比如crossFade()淡入淡出
        Glide.with(this).load(imageUrl).animate(R.anim.item_alpha_in).into(imageView);

        //设置缩略图支持
        //这样会先加载缩略图 然后再加载全图
        Glide.with(this).load(imageUrl).thumbnail(0.1f).into(imageView);

        //设置加载尺寸
        Glide.with(this).load(imageUrl).override(800, 800).into(imageView);

        //设置动态转换
        Glide.with(this).load(imageUrl).centerCrop().into(imageView);

        //api提供了比如：centerCrop()、fitCenter()等函数也可以通过自定义Transformation，举例说明：比如一个人圆角转化器
        //Also can set a multiple transformations.
        //bitmapTransform(new BlurTransformation(context, 25), new CropCircleTransformation(context))
        Glide.with(this).load(imageUrl).transform(new GlideRoundTransform(this)).into(imageView);

        //设置要加载的内容
        //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排，该如何实现目标下
        Glide.with(this).load(imageUrl).centerCrop().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                imageView.setImageDrawable(resource);
            }
        });

        //设置监听请求接口
        //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘
        Glide.with(this).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //imageView.setImageDrawable(resource);
                return false;
            }
        }).into(imageView);

        //设置动态GIF加载方式
        Glide.with(this).load(imageUrl).asBitmap().into(imageView);//显示gif静态图片
        Glide.with(this).load(imageUrl).asGif().into(imageView);//显示gif动态图片

        //缓存的动态清理
        Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
        Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }
}
