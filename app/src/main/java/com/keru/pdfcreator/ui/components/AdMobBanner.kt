package com.keru.pdfcreator.ui.components

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    // Test ad unit ID, replace after production!
    adUnitId: String = "ca-app-pub-3940256099942544/6300978111"
) {
    val context = LocalContext.current
    val adView = remember { createAdView(context, adUnitId) }

    AndroidView(
        factory = { adView },
        modifier = modifier
    )
}

private fun createAdView(context: Context, adUnitId: String): AdView {
    return AdView(context).apply {
        setAdSize(AdSize.BANNER)
        this.adUnitId = adUnitId
        loadAd(AdRequest.Builder().build())
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }
}