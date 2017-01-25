package org.voiddog.zoomabledrawee;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class ZoomViewPagerAdapter extends PagerAdapter {

    List<View> zoomDrawables = new ArrayList<>();

    public ZoomViewPagerAdapter(Context context){
        ZoomableDrawee zoomableDrawee;
        zoomDrawables.add(zoomableDrawee = new ZoomableDrawee(context));
        zoomableDrawee.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        zoomDrawables.add(zoomableDrawee = new ZoomableDrawee(context));
        zoomableDrawee.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        zoomDrawables.add(zoomableDrawee = new ZoomableDrawee(context));
        zoomableDrawee.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
    }

    @Override
    public int getCount() {
        return zoomDrawables.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ZoomableDrawee zoomableDrawee = (ZoomableDrawee) zoomDrawables.get(position);

        if(0==position){
            zoomableDrawee.setImageURI(Uri.parse("https://p.bre600708.com/cmsContent/20161215/137a255b-9f44-4e1a-9a4a-344d38abb0f5.jpg"));
        }else{
            zoomableDrawee.setImageURI(Uri.parse("http://voiddog.qiniudn.com/rem.jpg"));
        }

        zoomableDrawee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","position="+position);
            }
        });

        container.addView(zoomDrawables.get(position), new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return zoomDrawables.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(zoomDrawables.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}