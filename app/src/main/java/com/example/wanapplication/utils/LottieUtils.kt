package com.example.wanapplication.utils

import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class LottieUtils {
    fun getLottieDrawable(
        animation: LottieAnimation,
        bottomNavigationView: BottomNavigationView
    ): LottieDrawable {
        return LottieDrawable().apply {
            val result = LottieCompositionFactory.fromRawRes(
                bottomNavigationView.context.applicationContext, animation.value
            )
            callback = bottomNavigationView
        }
    }
}