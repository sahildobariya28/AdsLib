package com.compose.adslib.google

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.isVisible
import com.compose.adslib.databinding.AdmobNativeBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


class GoogleAdContainer : ComponentActivity() {

    var mInterstitialAd: InterstitialAd? = null
    var rewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val adType = intent.getStringExtra("AdsType")

            MobileAds.initialize(this) {}


            Column(Modifier.fillMaxSize()) {

                when (adType) {
                    "Banner" -> { LoadBanner() }

                    "Interstitial" -> { LoadInterstitial() }

                    "Native" -> { LoadNative() }

                    "Rewarded" -> { LoadRewarded() }

                    "Open Ad" -> {}

                    else -> { LoadBanner() }
                }

            }
        }

    }

    @Composable
    fun LoadBanner() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var isClick by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(false) }

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
                Text(
                    text = "Google Admob Banner Ads",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Green
                )

                Spacer(modifier = Modifier.height(15.dp))

                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { context ->


                        AdView(context).apply {
                            this.setAdSize(AdSize.BANNER)
                            adUnitId = "ca-app-pub-3940256099942544/6300978111"
                            loadAd(AdRequest.Builder().build())

                            this.adListener = object : AdListener() {
                                override fun onAdClicked() {
                                }

                                override fun onAdClosed() {
                                }

                                override fun onAdFailedToLoad(adError: LoadAdError) {
                                }

                                override fun onAdImpression() {

                                }

                                override fun onAdLoaded() {
                                    isLoading = false
                                }

                                override fun onAdOpened() {
                                }
                            }
                        }
                    }
                )
            }

        }

    }

    @Composable
    fun LoadInterstitial() {

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var isClick by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(false) }

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
                val adRequest = AdRequest.Builder().build()

                InterstitialAd.load(
                    this@GoogleAdContainer,
                    "ca-app-pub-3940256099942544/1033173712",
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            mInterstitialAd = null
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            mInterstitialAd = interstitialAd
                            if (mInterstitialAd != null) {
                                mInterstitialAd?.show(this@GoogleAdContainer)

                                mInterstitialAd?.fullScreenContentCallback =
                                    object : FullScreenContentCallback() {
                                        override fun onAdClicked() {}

                                        override fun onAdDismissedFullScreenContent() {
                                            mInterstitialAd = null
                                        }

                                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                            mInterstitialAd = null
                                        }

                                        override fun onAdImpression() {}

                                        override fun onAdShowedFullScreenContent() {
                                            isLoading = false
                                        }
                                    }
                            }
                        }
                    })


            }
        }
    }

    @Composable
    fun LoadNative() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var isClick by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(false) }

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


                AndroidViewBinding(
                    factory = AdmobNativeBinding::inflate
                ) {
                    val adView = root.also { adview ->
                        adview.bodyView = this.adBody
                        adview.callToActionView = this.adCallToAction
                        adview.headlineView = this.adHeadline
                        adview.iconView = this.adAppIcon
                    }

                    val adContainer = this.container
                    val adParent = this.parentNative

                    // Request Ad
                    val adLoader =
                        AdLoader.Builder(adView.context, "ca-app-pub-3940256099942544/2247696110")
                            .forNativeAd { nativeAd ->
                                nativeAd.advertiser?.let {

                                }
                                nativeAd.body?.let { body ->
                                    this.adBody.text = body
                                }
                                nativeAd.callToAction?.let { callToAction ->
                                    this.adCallToAction.text = callToAction
                                }
                                nativeAd.headline?.let { headline ->
                                    this.adHeadline.text = headline
                                }
                                nativeAd.icon?.let { icon ->
                                    this.adAppIcon.setImageDrawable(icon.drawable)
                                }
                                adView.setNativeAd(nativeAd)

                            }.withAdListener(object : AdListener() {
                                override fun onAdLoaded() {
                                    isLoading = false
                                    Log.i("djjkdsjf", "onAdLoaded: Native Ad loaded")
                                    adContainer.isVisible = false
                                    super.onAdLoaded()
                                }

                                override fun onAdFailedToLoad(error: LoadAdError) {
                                    isLoading = false
                                    Log.i("djjkdsjf", "onAdFailedToLoad: ${error.message}")
                                    adParent.isVisible = false
                                    super.onAdFailedToLoad(error)
                                }
                            }).withNativeAdOptions(
                                NativeAdOptions.Builder().setAdChoicesPlacement(
                                    NativeAdOptions.ADCHOICES_TOP_RIGHT
                                ).build()
                            ).build()
                    adContainer.isVisible = true
                    adLoader.loadAd(AdRequest.Builder().build())
                }
            }
        }
    }

    @Composable
    fun LoadRewarded() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var isClick by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(false) }

            Button(onClick = {
                isClick = !isClick
            }) {
                Text(text = "Load Rewarded")
                Spacer(modifier = Modifier.width(10.dp))
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White)
                }

            }
            if (isClick) {
                isLoading = true

                val adRequest = AdRequest.Builder().build()
                RewardedAd.load(this@GoogleAdContainer,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        rewardedAd = ad
                        rewardedAd?.let { rewardAd ->
                            rewardAd.show(this@GoogleAdContainer) { rewardItem ->
                                // Handle the reward.
                                val rewardAmount = rewardItem.amount
                                val rewardType = rewardItem.type
                                Log.d(
                                    "djjkdsjf",
                                    "User earned the reward. amount: $rewardAmount, type: $rewardType"
                                )
                            }
                        } ?: run {
                            Log.d("djjkdsjf", "The rewarded ad wasn't ready yet.")
                        }
                        rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                            override fun onAdClicked() {}

                            override fun onAdDismissedFullScreenContent() {
                                rewardedAd = null
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                rewardedAd = null
                            }

                            override fun onAdImpression() {}

                            override fun onAdShowedFullScreenContent() {
                                isLoading = false
                            }
                        }
                    }
                })
            }
        }
    }


}