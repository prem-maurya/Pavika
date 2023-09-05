package com.prem.pavikaa.Ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Admob {

    static OnDismiss onDismiss;

    public Admob(OnDismiss onDismiss) {
        this.onDismiss = onDismiss;
    }

    public Admob(){

    }

    public static void setBanner(LinearLayout banner, Context context) {
        if (AdsUnit.isAds && AdsUnit.isBannerAds) {

            MobileAds.initialize(context, initializationStatus -> {
            });

            AdView adView = new AdView(context);
            banner.addView(adView);

            adView.setAdUnitId(AdsUnit.BANNER);
            adView.setAdSize(AdSize.BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

        }
    }
        public static void loadInterstitialsAds (Context context) {
            if (AdsUnit.isAds && AdsUnit.isInterstitialAds) {

                MobileAds.initialize(context, initializationStatus -> {
                });

                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(context, AdsUnit.INTERSTITIAL, adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                                AdsUnit.mInterstitialAd = interstitialAd;
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                AdsUnit.mInterstitialAd = null;
                            }
                        });
            }
        }

        public  void showInterstitialsAds(Activity activity, boolean isReload){

            if (AdsUnit.mInterstitialAd != null) {
                AdsUnit.mInterstitialAd.show(activity);

                AdsUnit.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();

                        if(isReload){
                            AdsUnit.mInterstitialAd = null;
                            Admob.loadInterstitialsAds(activity);
                        }
                        onDismiss.onDismiss();

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        onDismiss.onDismiss();
                    }
                });

            } else {
                onDismiss.onDismiss();
            }

        }
    public static void showOpenAds(Activity activity){

        Application application = activity.getApplication();
        ((OpenAds)application).showAdIfAvailable(activity, new OpenAds.OnShowAdCompleteListener() {
            @Override
            public void onShowAdComplete() {
            }
        });
    }
}

