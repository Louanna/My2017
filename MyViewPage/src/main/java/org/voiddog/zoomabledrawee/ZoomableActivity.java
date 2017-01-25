package org.voiddog.zoomabledrawee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * 文件描述
 *
 * @author qigengxin
 * @since 2016-11-14 14:39
 */
public class ZoomableActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomable);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new ZoomViewPagerAdapter(viewPager.getContext()));
    }

}
