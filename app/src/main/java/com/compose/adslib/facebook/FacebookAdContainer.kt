package com.compose.adslib.facebook

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.compose.adslib.R
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdOptionsView
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdLayout
import com.facebook.ads.NativeAdListener
import com.facebook.ads.RewardedVideoAd
import com.facebook.ads.RewardedVideoAdListener








class FacebookAdContainer : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val nativeAd = NativeAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
            val interstitialAd = InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
            val bannerAd = AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
            val rewardedVideoAd = RewardedVideoAd(this, "VID_HD_9_16_39S_APP_INSTALL#YOUR_PLACEMENT_ID")

            val adType = intent.getStringExtra("AdsType")

            AudienceNetworkAds.initialize(this)


            Column(Modifier.fillMaxSize()) {

                when (adType) {
                    "Banner" -> {
                        LoadBanner(bannerAd)
                    }

                    "Interstitial" -> {
                        LoadInterstitial(interstitialAd)
                    }

                    "Native" -> {
                        LoadNative(nativeAd)
                    }

                    "Rewarded" -> {
                        LoadRewarded(rewardedVideoAd)
                    }

                    else -> {
                        LoadBanner(bannerAd)
                    }
                }

            }
        }
    }

    @Composable
    fun LoadBanner(bannerAd: AdView) {
        var isClick by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                isClick = !isClick
            }) {
                Text(text = "Load Banner")
                Spacer(modifier = Modifier.width(10.dp))
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White)
                }

            }
            if (isClick) {
                isLoading = true

                val adListener: AdListener = object : AdListener {
                    override fun onError(ad: Ad?, adError: AdError) {
                        // Ad error callback
                        Toast.makeText(
                            this@FacebookAdContainer,
                            "Error: " + adError.errorMessage,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    override fun onAdLoaded(ad: Ad?) {
                        isLoading = false
                        // Ad loaded callback
                    }

                    override fun onAdClicked(ad: Ad?) {
                        // Ad clicked callback
                    }

                    override fun onLoggingImpression(ad: Ad?) {
                        // Ad impression logged callback
                    }
                }

                AndroidView(
                    factory = {
                        View.inflate(it, R.layout.facebook_banner, null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    update = {
                        val adContainer = it.findViewById<LinearLayout>(R.id.banner_container)
                        adContainer.addView(bannerAd)
                    }
                )
                LaunchedEffect(key1 = Unit) {
                    bannerAd.loadAd(bannerAd.buildLoadAdConfig().withAdListener(adListener).build())
                }
            }
        }


    }

    @Composable
    fun LoadInterstitial(interstitialAd: InterstitialAd) {

        var isClick by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                isClick = !isClick
            }) {
                Text(text = "Load Interstitial")
                Spacer(modifier = Modifier.width(10.dp))
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White)
                }

            }
            if (isClick) {
                isLoading = true

                val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
                    override fun onInterstitialDisplayed(ad: Ad) {
                        // Interstitial ad displayed callback
                        Log.e("dhdhdhhee123", "Interstitial ad displayed.")
                    }

                    override fun onInterstitialDismissed(ad: Ad) {
                        // Interstitial dismissed callback
                        Log.e("dhdhdhhee123", "Interstitial ad dismissed.")
                    }

                    override fun onError(ad: Ad, adError: AdError) {
                        // Ad error callback
                        Log.e("dhdhdhhee123", "Interstitial ad failed to load: " + adError.errorMessage)
                    }

                    override fun onAdLoaded(ad: Ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d("dhdhdhhee123", "Interstitial ad is loaded and ready to be displayed!")
                        // Show the ad
                        interstitialAd.show()
                        isLoading = false
                    }

                    override fun onAdClicked(ad: Ad) {
                        // Ad clicked callback
                        Log.d("dhdhdhhee123", "Interstitial ad clicked!")
                    }

                    override fun onLoggingImpression(ad: Ad) {
                        // Ad impression logged callback
                        Log.d("dhdhdhhee123", "Interstitial ad impression logged!")
                    }
                }

                interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build()
                )
            }
        }

    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun LoadNative(nativeAd: NativeAd) {
        Log.d("dhdhdhhee123", "yes i am run 1")
        var isClick by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        val isLoadingSuccess = remember { mutableStateOf(false) }


        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                isClick = !isClick
            }) {
                Text(text = "Load Native")
                Spacer(modifier = Modifier.width(10.dp))
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White)
                }

            }
            if (isClick) {
                isLoading = true


                val nativeAdListener: NativeAdListener = object : NativeAdListener {
                    override fun onMediaDownloaded(ad: Ad?) {
                        Log.e("dhdhdhhee123", "Native ad finished downloading all assets.")
                    }

                    override fun onError(ad: Ad?, adError: AdError) {
                        Log.e("dhdhdhhee123", "Native ad failed to load: " + adError.errorMessage)
                    }

                    override fun onAdLoaded(ad: Ad?) {
                        Log.d("dhdhdhhee123", "Native ad is loaded and ready to be displayed! ")
                        if (nativeAd != ad) {
                            return
                        }

                        isLoadingSuccess.value = true

                    }

                    override fun onAdClicked(ad: Ad?) {
                        Log.d("dhdhdhhee123", "Native ad clicked!")
                    }

                    override fun onLoggingImpression(ad: Ad?) {
                        Log.d("dhdhdhhee123", "Native ad impression logged!")
                    }
                }

                if (isLoadingSuccess.value) {
                    isLoading = false
                    AndroidView(
                        factory = {
                            View.inflate(it, R.layout.facebook_native, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        update = {
                            nativeAd.unregisterView()

                            // Create native Container UI using the ad metadata.
                            val nativeAdLayout =
                                it.findViewById<NativeAdLayout>(R.id.native_ad_container)

                            // Add the AdOptionsView
                            val adChoicesContainer =
                                it.findViewById<LinearLayout>(R.id.ad_choices_container)
                            val adOptionsView =
                                AdOptionsView(this@FacebookAdContainer, nativeAd, nativeAdLayout)
                            adChoicesContainer.removeAllViews()
                            adChoicesContainer.addView(adOptionsView, 0)

                            // Create native UI using the ad metadata.
                            val nativeAdIcon = it.findViewById<MediaView>(R.id.native_ad_icon)
                            val nativeAdTitle = it.findViewById<TextView>(R.id.native_ad_title)
                            val nativeAdMedia = it.findViewById<MediaView>(R.id.native_ad_media)
                            val nativeAdSocialContext =
                                it.findViewById<TextView>(R.id.native_ad_social_context)
                            val nativeAdBody = it.findViewById<TextView>(R.id.native_ad_body)
                            val sponsoredLabel =
                                it.findViewById<TextView>(R.id.native_ad_sponsored_label)
                            val nativeAdCallToAction =
                                it.findViewById<Button>(R.id.native_ad_call_to_action)

                            // Set the Text.
                            nativeAdTitle.text = (nativeAd.advertiserName)
                            nativeAdBody.text = nativeAd.adBodyText
                            nativeAdSocialContext.text = nativeAd.adSocialContext
                            nativeAdCallToAction.visibility =
                                if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
                            nativeAdCallToAction.text = nativeAd.adCallToAction
                            sponsoredLabel.text = nativeAd.sponsoredTranslation

                            // Create a list of clickable views
                            val clickableViews: MutableList<View> = ArrayList()
                            clickableViews.add(nativeAdTitle)
                            clickableViews.add(nativeAdCallToAction)

                            // Register the Title and CTA button to listen for clicks.
                            nativeAd.registerViewForInteraction(
                                it,
                                nativeAdMedia,
                                nativeAdIcon,
                                clickableViews
                            )
                        },
                    )
                }
                LaunchedEffect(key1 = Unit) {
                    Log.d("dhdhdhhee123", "yes i am run 2")
                    nativeAd.loadAd(
                        nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build()
                    )
                }
            }
        }
    }

    @Composable
    fun LoadRewarded(rewardedVideoAd: RewardedVideoAd) {
        var isClick by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                isClick = !isClick
            }) {
                Text(text = "Load Reward")
                Spacer(modifier = Modifier.width(10.dp))
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White)
                }

            }
            if (isClick) {
                isLoading = true

                val rewardedVideoAdListener: RewardedVideoAdListener = object : RewardedVideoAdListener {
                    override fun onError(ad: Ad, error: AdError) {
                        // Rewarded video ad failed to load
                        Log.e("dhdhdhhee123", "Rewarded video ad failed to load: " + error.errorMessage)
                    }

                    override fun onAdLoaded(ad: Ad) {
                        // Rewarded video ad is loaded and ready to be displayed
                        Log.d("dhdhdhhee123", "Rewarded video ad is loaded and ready to be displayed!")
                        rewardedVideoAd.show()
                        isLoading = false
                    }

                    override fun onAdClicked(ad: Ad) {
                        // Rewarded video ad clicked
                        Log.d("dhdhdhhee123", "Rewarded video ad clicked!")
                    }

                    override fun onLoggingImpression(ad: Ad) {
                        // Rewarded Video ad impression - the event will fire when the
                        // video starts playing
                        Log.d("dhdhdhhee123", "Rewarded video ad impression logged!")
                    }

                    override fun onRewardedVideoCompleted() {
                        // Rewarded Video View Complete - the video has been played to the end.
                        // You can use this event to initialize your reward
                        Log.d("dhdhdhhee123", "Rewarded video completed!")
                        // Call method to give reward
                        // giveReward();
                    }

                    override fun onRewardedVideoClosed() {
                        // The Rewarded Video ad was closed - this can occur during the video
                        // by closing the app, or closing the end card.
                        Log.d("dhdhdhhee123", "Rewarded video ad closed!")
                    }
                }

                rewardedVideoAd.loadAd(
                    rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build())
            }
        }


    }
}