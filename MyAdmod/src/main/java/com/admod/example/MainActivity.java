package com.admod.example;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            // 横幅广告
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
//            mAdView.setAdSize(AdSize.FULL_BANNER);
            mAdView.loadAd(adRequest);
            // 插页式广告
//            final InterstitialAd interstitial = new InterstitialAd(
//                    this);
//            interstitial.setAdUnitId(getString(R.string.banner_ad_unit_id));
//            if (interstitial.isLoaded()) {
//                interstitial.show();
//
//            } else {
//                AdRequest adRequest1 = new AdRequest.Builder().build();
//                interstitial.loadAd(adRequest1);
//                AdListener adListener = new AdListener() {
//
//                    @Override
//                    public void onAdLoaded() {
//                        // TODO Auto-generated method stub
//                        super.onAdLoaded();
//                        interstitial.show();
//                    }
//                };
//                interstitial.setAdListener(adListener);
//            }
//
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }

    }

}
